/**
 * Civic Connect Citizen Dashboard Handler
 */

let selectedLat = 28.6139;
let selectedLng = 77.2090;

document.addEventListener('DOMContentLoaded', async () => {
    const currentUser = window.CivicAuth.requireAuth(['ROLE_CITIZEN', 'ROLE_ADMIN']);
    if (!currentUser) return;

    document.getElementById('welcomeName').textContent = currentUser.fullName;

    // Initialize Map Picker on Modal Open
    const submitModal = document.getElementById('submitModal');
    if (submitModal) {
        submitModal.addEventListener('shown.bs.modal', () => {
            window.CivicMap.initLocationPicker('pickerMap', (lat, lng) => {
                selectedLat = lat;
                selectedLng = lng;
                document.getElementById('latInput').value = lat.toFixed(5);
                document.getElementById('lngInput').value = lng.toFixed(5);
            });
        });
    }

    // Photo Preview Handler
    const photoInput = document.getElementById('photoInput');
    if (photoInput) {
        photoInput.addEventListener('change', (e) => {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(evt) {
                    const preview = document.getElementById('photoPreview');
                    preview.src = evt.target.result;
                    preview.classList.remove('d-none');
                };
                reader.readAsDataURL(file);
            }
        });
    }

    // Submit Complaint Form Handler
    const complaintForm = document.getElementById('complaintForm');
    if (complaintForm) {
        complaintForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const title = document.getElementById('titleInput').value;
            const categoryId = document.getElementById('categorySelect').value;
            const description = document.getElementById('descriptionInput').value;
            const address = document.getElementById('addressInput').value;
            const photoPreview = document.getElementById('photoPreview');
            const photoUrl = photoPreview && !photoPreview.classList.contains('d-none') ? photoPreview.src : null;

            try {
                await window.CivicApi.submitComplaint({
                    title,
                    categoryId,
                    description,
                    address,
                    latitude: selectedLat,
                    longitude: selectedLng,
                    photoUrl
                });

                alert('Complaint submitted successfully!');
                bootstrap.Modal.getInstance(submitModal).hide();
                complaintForm.reset();
                loadCitizenData();
            } catch (err) {
                alert('Failed to submit complaint: ' + err.message);
            }
        });
    }

    loadCitizenData();
});

async function loadCitizenData() {
    try {
        const complaints = await window.CivicApi.getComplaints();
        renderMyComplaintsTable(complaints);
        
        // Initialize Overview Map
        const map = window.CivicMap.init('map');
        window.CivicMap.renderComplaintMarkers(map, complaints);
    } catch (err) {
        console.error('Error loading complaints:', err);
    }
}

function renderMyComplaintsTable(complaints) {
    const tbody = document.getElementById('complaintsTableBody');
    if (!tbody) return;

    if (!complaints || complaints.length === 0) {
        tbody.innerHTML = `<tr><td colspan="6" class="text-center text-muted py-4">No complaints submitted yet. Click "Submit New Complaint" to create one.</td></tr>`;
        return;
    }

    tbody.innerHTML = complaints.map(c => {
        const dateStr = new Date(c.createdAt).toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
        const slaDiffHours = Math.round((new Date(c.slaDeadline) - new Date()) / (1000 * 3600));
        let slaBadgeClass = 'sla-normal';
        let slaText = `${slaDiffHours}h remaining`;
        if (slaDiffHours < 0) {
            slaBadgeClass = 'sla-breached';
            slaText = `SLA Breached (${Math.abs(slaDiffHours)}h overdue)`;
        } else if (slaDiffHours <= 12) {
            slaBadgeClass = 'sla-warning';
        }

        const getStatusBadge = (status) => {
            const map = {
                'SUBMITTED': 'badge-submitted',
                'ASSIGNED': 'badge-assigned',
                'IN_PROGRESS': 'badge-in-progress',
                'RESOLVED': 'badge-resolved',
                'CLOSED': 'badge-closed',
                'REOPENED': 'badge-reopened'
            };
            return map[status] || 'bg-secondary';
        };

        return `
            <tr>
                <td><strong class="text-primary">${c.complaintNumber}</strong></td>
                <td>
                    <div class="fw-bold">${c.title}</div>
                    <small class="text-muted">${c.categoryName}</small>
                </td>
                <td><span class="badge ${getStatusBadge(c.status)}">${c.status}</span></td>
                <td><span class="sla-badge ${slaBadgeClass}">${slaText}</span></td>
                <td><small class="text-muted">${dateStr}</small></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" onclick="window.viewComplaintDetails(${c.id})">
                        <i class="bi bi-eye"></i> View Timeline
                    </button>
                    ${c.status === 'RESOLVED' ? `
                        <button class="btn btn-sm btn-success ms-1" onclick="window.verifyResolution(${c.id}, 'confirm')">Confirm</button>
                        <button class="btn btn-sm btn-outline-warning ms-1" onclick="window.verifyResolution(${c.id}, 'reopen')">Reopen</button>
                    ` : ''}
                </td>
            </tr>
        `;
    }).join('');
}

window.viewComplaintDetails = async function(id) {
    const complaints = await window.CivicApi.getComplaints();
    const complaint = complaints.find(c => c.id === Number(id));
    if (complaint) {
        window.CivicTimeline.render('timelineContainer', complaint);
        const modal = new bootstrap.Modal(document.getElementById('timelineModal'));
        modal.show();
    }
};

window.verifyResolution = async function(id, action) {
    const comment = prompt(action === 'confirm' ? 'Leave an optional satisfaction feedback:' : 'Please provide the reason for reopening this issue:');
    if (comment !== null) {
        const newStatus = action === 'confirm' ? 'CLOSED' : 'REOPENED';
        await window.CivicApi.updateStatus(id, newStatus, comment);
        alert(action === 'confirm' ? 'Resolution confirmed! Complaint closed.' : 'Complaint reopened. Assigned team notified.');
        loadCitizenData();
    }
};
