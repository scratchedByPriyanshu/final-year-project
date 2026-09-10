/**
 * Civic Connect Officer Workstation Handler
 */

document.addEventListener('DOMContentLoaded', async () => {
    const currentUser = window.CivicAuth.requireAuth(['ROLE_OFFICER', 'ROLE_ADMIN']);
    if (!currentUser) return;

    document.getElementById('welcomeOfficerName').textContent = currentUser.fullName;
    document.getElementById('deptName').textContent = currentUser.departmentName || 'Roads & Infrastructure';

    loadOfficerComplaints();
});

async function loadOfficerComplaints() {
    try {
        const complaints = await window.CivicApi.getComplaints();
        renderOfficerTable(complaints);
    } catch (err) {
        console.error('Error loading officer complaints:', err);
    }
}

function renderOfficerTable(complaints) {
    const tbody = document.getElementById('officerTableBody');
    if (!tbody) return;

    if (!complaints || complaints.length === 0) {
        tbody.innerHTML = `<tr><td colspan="6" class="text-center text-muted py-4">No complaints assigned to your department queue.</td></tr>`;
        return;
    }

    tbody.innerHTML = complaints.map(c => {
        return `
            <tr>
                <td><strong class="text-primary">${c.complaintNumber}</strong></td>
                <td>
                    <div class="fw-bold">${c.title}</div>
                    <small class="text-muted">${c.categoryName} • ${c.wardName}</small>
                </td>
                <td><span class="badge bg-${getStatusColor(c.status)}">${c.status}</span></td>
                <td><small class="text-muted">${c.citizenName}</small></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" onclick="window.viewComplaintDetails(${c.id})">
                        <i class="bi bi-eye"></i> View Timeline
                    </button>
                    ${renderOfficerActionButtons(c)}
                </td>
            </tr>
        `;
    }).join('');
}

function getStatusColor(status) {
    switch (status) {
        case 'SUBMITTED': return 'secondary';
        case 'ASSIGNED': return 'info';
        case 'IN_PROGRESS': return 'warning';
        case 'RESOLVED': return 'success';
        case 'CLOSED': return 'dark';
        default: return 'primary';
    }
}

function renderOfficerActionButtons(c) {
    if (c.status === 'SUBMITTED' || c.status === 'ASSIGNED') {
        return `<button class="btn btn-sm btn-warning ms-1" onclick="window.updateStatusPrompt(${c.id}, 'IN_PROGRESS')">Accept & Start</button>`;
    } else if (c.status === 'IN_PROGRESS' || c.status === 'REOPENED') {
        return `<button class="btn btn-sm btn-success ms-1" onclick="window.updateStatusPrompt(${c.id}, 'RESOLVED')">Mark Resolved</button>`;
    }
    return '';
}

window.updateStatusPrompt = async function(id, newStatus) {
    const comment = prompt(`Enter progress updates / resolution notes for status ${newStatus}:`);
    if (comment !== null) {
        await window.CivicApi.updateStatus(id, newStatus, comment);
        alert(`Complaint status updated to ${newStatus}!`);
        loadOfficerComplaints();
    }
};

window.viewComplaintDetails = async function(id) {
    const complaints = await window.CivicApi.getComplaints();
    const complaint = complaints.find(c => c.id === Number(id));
    if (complaint) {
        window.CivicTimeline.render('timelineContainer', complaint);
        const modal = new bootstrap.Modal(document.getElementById('timelineModal'));
        modal.show();
    }
};
