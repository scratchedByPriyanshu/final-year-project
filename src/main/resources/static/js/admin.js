/**
 * Civic Connect Admin Dashboard & Analytics Handler (Feature 2C)
 */

let categoryChartInstance = null;
let statusChartInstance = null;

document.addEventListener('DOMContentLoaded', async () => {
    const currentUser = window.CivicAuth.requireAuth(['ROLE_ADMIN']);
    if (!currentUser) return;

    loadAdminDashboard();
});

async function loadAdminDashboard() {
    try {
        const analytics = await window.CivicApi.getAnalytics();
        const complaints = await window.CivicApi.getComplaints();

        // Render KPI Stat Cards (Feature 2C)
        document.getElementById('statTotal').textContent = analytics.totalComplaints;
        document.getElementById('statPending').textContent = analytics.pendingComplaints;
        document.getElementById('statInProgress').textContent = analytics.inProgressComplaints;
        document.getElementById('statResolved').textContent = analytics.resolvedComplaints;

        // Render Analytics Charts using Chart.js
        renderCategoryChart(analytics.categoryDistribution);
        renderStatusChart(analytics.statusDistribution);

        // Render All Complaints Admin Table
        renderAdminComplaintsTable(complaints);

        // Render Overview GIS Map
        const map = window.CivicMap.init('adminMap');
        window.CivicMap.renderComplaintMarkers(map, complaints);
    } catch (err) {
        console.error('Error loading admin analytics:', err);
    }
}

function renderCategoryChart(categoryData) {
    const ctx = document.getElementById('categoryChart');
    if (!ctx) return;

    if (categoryChartInstance) {
        categoryChartInstance.destroy();
    }

    const labels = Object.keys(categoryData || { 'Pothole': 14, 'Garbage': 9, 'Streetlight': 6, 'Water': 4 });
    const data = Object.values(categoryData || { 'Pothole': 14, 'Garbage': 9, 'Streetlight': 6, 'Water': 4 });

    categoryChartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Complaints by Category',
                data: data,
                backgroundColor: ['#0d6efd', '#ffc107', '#0dcaf0', '#198754', '#6c757d'],
                borderRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: { beginAtZero: true, ticks: { precision: 0 } }
            }
        }
    });
}

function renderStatusChart(statusData) {
    const ctx = document.getElementById('statusChart');
    if (!ctx) return;

    if (statusChartInstance) {
        statusChartInstance.destroy();
    }

    const labels = Object.keys(statusData || { 'Submitted': 12, 'In Progress': 18, 'Resolved': 35 });
    const data = Object.values(statusData || { 'Submitted': 12, 'In Progress': 18, 'Resolved': 35 });

    statusChartInstance = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: ['#6c757d', '#0dcaf0', '#ffc107', '#198754', '#212529']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { position: 'bottom' }
            }
        }
    });
}

function renderAdminComplaintsTable(complaints) {
    const tbody = document.getElementById('adminTableBody');
    if (!tbody) return;

    tbody.innerHTML = complaints.map(c => {
        return `
            <tr>
                <td><strong class="text-primary">${c.complaintNumber}</strong></td>
                <td>
                    <div class="fw-bold">${c.title}</div>
                    <small class="text-muted">${c.categoryName} • ${c.wardName}</small>
                </td>
                <td><span class="badge bg-secondary">${c.departmentName}</span></td>
                <td><span class="badge bg-info text-dark">${c.status}</span></td>
                <td><small>${c.officerName}</small></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" onclick="window.viewComplaintDetails(${c.id})">
                        <i class="bi bi-eye"></i> View Timeline
                    </button>
                    <button class="btn btn-sm btn-outline-dark ms-1" onclick="window.reassignDepartment(${c.id})">Reassign</button>
                </td>
            </tr>
        `;
    }).join('');
}

window.reassignDepartment = async function(id) {
    const dept = prompt('Enter new Department name (e.g. Roads & Infrastructure, Sanitation & Solid Waste, Electrical & Lighting, Water & Sewage):');
    if (dept) {
        await window.CivicApi.updateStatus(id, 'ASSIGNED', `Reassigned to ${dept}`);
        alert('Department reassigned successfully!');
        loadAdminDashboard();
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
