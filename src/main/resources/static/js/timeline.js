/**
 * Civic Connect Complaint Timeline Visualizer (Feature 2B)
 */

window.CivicTimeline = {
    render(containerId, complaint) {
        const container = document.getElementById(containerId);
        if (!container || !complaint) return;

        let html = `
            <div class="mb-3 p-3 bg-light rounded border">
                <div class="d-flex justify-content-between align-items-center mb-2">
                    <span class="badge bg-primary fs-6">${complaint.complaintNumber}</span>
                    <span class="badge bg-dark">${complaint.categoryName}</span>
                </div>
                <h5 class="fw-bold text-dark mb-1">${complaint.title}</h5>
                <p class="text-muted small mb-2">${complaint.description}</p>
                <div class="row g-2 small">
                    <div class="col-6"><strong>Ward:</strong> ${complaint.wardName} (${complaint.wardNumber})</div>
                    <div class="col-6"><strong>Department:</strong> ${complaint.departmentName}</div>
                    <div class="col-6"><strong>Assigned Officer:</strong> ${complaint.officerName || 'Unassigned'}</div>
                    <div class="col-6"><strong>Citizen:</strong> ${complaint.citizenName}</div>
                </div>
            </div>
            
            <h6 class="fw-bold border-bottom pb-2 mb-3"><i class="bi bi-clock-history me-1"></i> Accountability Progress Timeline</h6>
            <div class="timeline-tree">
        `;

        if (complaint.timeline && complaint.timeline.length > 0) {
            complaint.timeline.forEach((step, index) => {
                const isLast = index === complaint.timeline.length - 1;
                const dateStr = new Date(step.createdAt).toLocaleString('en-US', {
                    day: 'numeric', month: 'short', hour: '2-digit', minute: '2-digit'
                });

                let statusBadge = step.status;
                let stepClass = isLast ? 'in-progress' : 'completed';
                if (step.status === 'RESOLVED' || step.status === 'CLOSED') {
                    stepClass = 'completed';
                }

                html += `
                    <div class="timeline-step ${stepClass}">
                        <div class="timeline-node"></div>
                        <div class="timeline-content">
                            <div class="d-flex justify-content-between align-items-center mb-1">
                                <span class="timeline-title text-dark">● ${step.status}</span>
                                <span class="timeline-time">${dateStr}</span>
                            </div>
                            <p class="mb-0 text-secondary small">${step.comments || 'Status update logged.'}</p>
                            ${step.updatedByName ? `<small class="text-muted d-block mt-1">Updated by: <strong>${step.updatedByName}</strong></small>` : ''}
                            ${step.photoProof ? `<img src="${step.photoProof}" class="img-fluid rounded mt-2" style="max-height: 140px;" alt="Work Proof">` : ''}
                        </div>
                    </div>
                `;
            });
        } else {
            html += `<p class="text-muted small">No history logged yet.</p>`;
        }

        html += `</div>`;
        container.innerHTML = html;
    }
};
