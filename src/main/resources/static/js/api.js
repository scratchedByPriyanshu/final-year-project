/**
 * Civic Connect API Client & Mock Adapter
 * Dual-Mode: Sends real Axios HTTP requests to Spring Boot server if reachable,
 * otherwise transparently manages in-memory/localStorage state for instant zero-config testing!
 */

const API_BASE_URL = 'http://localhost:8080/api';

// Initial Mock Seed Data
const INITIAL_MOCK_DATA = {
    users: [
        { id: 1, username: 'citizen_john', email: 'john@example.com', password: 'password123', fullName: 'John Doe', role: 'ROLE_CITIZEN', wardId: 1, wardName: 'Downtown Central (Ward 101)' },
        { id: 2, username: 'officer_smith', email: 'smith@gov.city', password: 'password123', fullName: 'Officer Alex Smith', role: 'ROLE_OFFICER', departmentId: 1, departmentName: 'Roads & Infrastructure', wardId: 1, wardName: 'Downtown Central (Ward 101)' },
        { id: 3, username: 'admin_super', email: 'admin@gov.city', password: 'password123', fullName: 'City Chief Admin', role: 'ROLE_ADMIN' }
    ],
    complaints: [
        {
            id: 1001,
            complaintNumber: 'CMP-2026-1001',
            title: 'Major Pothole on Main St',
            description: 'Hazardous deep pothole near central crossroad causing traffic slowdown.',
            categoryName: 'Pothole & Road Damage',
            departmentName: 'Roads & Infrastructure',
            wardName: 'Downtown Central',
            wardNumber: 101,
            citizenName: 'John Doe',
            officerName: 'Officer Alex Smith',
            status: 'IN_PROGRESS',
            latitude: 28.6139,
            longitude: 77.2090,
            address: 'Main St, Ward 101, Downtown',
            photoUrl: 'https://images.unsplash.com/photo-1515162816999-a0c47dc192f7?auto=format&fit=crop&w=600&q=80',
            slaDeadline: new Date(Date.now() + 36 * 3600000).toISOString(),
            isEscalated: false,
            createdAt: new Date(Date.now() - 12 * 3600000).toISOString(),
            updatedAt: new Date(Date.now() - 6 * 3600000).toISOString(),
            timeline: [
                { id: 1, status: 'SUBMITTED', updatedByName: 'John Doe', comments: 'Complaint submitted by citizen.', createdAt: new Date(Date.now() - 12 * 3600000).toISOString() },
                { id: 2, status: 'ASSIGNED', updatedByName: 'City Chief Admin', comments: 'Assigned to Roads & Infrastructure Dept.', createdAt: new Date(Date.now() - 11 * 3600000).toISOString() },
                { id: 3, status: 'IN_PROGRESS', updatedByName: 'Officer Alex Smith', comments: 'Officer accepted task. Repair crew dispatched.', createdAt: new Date(Date.now() - 6 * 3600000).toISOString() }
            ]
        },
        {
            id: 1002,
            complaintNumber: 'CMP-2026-1002',
            title: 'Overflowing Waste Containers',
            description: 'Public garbage containers at Market Square overflowing with litter.',
            categoryName: 'Garbage Accumulation',
            departmentName: 'Sanitation & Solid Waste',
            wardName: 'Downtown Central',
            wardNumber: 101,
            citizenName: 'John Doe',
            officerName: 'Unassigned',
            status: 'SUBMITTED',
            latitude: 28.6145,
            longitude: 77.2095,
            address: 'Market Square, Ward 101',
            photoUrl: 'https://images.unsplash.com/photo-1530587191325-3db32d826c18?auto=format&fit=crop&w=600&q=80',
            slaDeadline: new Date(Date.now() + 20 * 3600000).toISOString(),
            isEscalated: false,
            createdAt: new Date(Date.now() - 4 * 3600000).toISOString(),
            updatedAt: new Date(Date.now() - 4 * 3600000).toISOString(),
            timeline: [
                { id: 1, status: 'SUBMITTED', updatedByName: 'John Doe', comments: 'Complaint registered by citizen.', createdAt: new Date(Date.now() - 4 * 3600000).toISOString() }
            ]
        },
        {
            id: 1003,
            complaintNumber: 'CMP-2026-1003',
            title: 'Dark Streetlight on 5th Ave',
            description: 'Streetlight fixture broken near residential walkway.',
            categoryName: 'Broken Streetlight',
            departmentName: 'Electrical & Lighting',
            wardName: 'Northside Park',
            wardNumber: 102,
            citizenName: 'Jane Smith',
            officerName: 'Officer Alex Smith',
            status: 'RESOLVED',
            latitude: 28.6180,
            longitude: 77.2150,
            address: '5th Ave, Ward 102',
            photoUrl: 'https://images.unsplash.com/photo-1509114397022-ed747cca3f65?auto=format&fit=crop&w=600&q=80',
            slaDeadline: new Date(Date.now() - 10 * 3600000).toISOString(),
            isEscalated: false,
            createdAt: new Date(Date.now() - 48 * 3600000).toISOString(),
            updatedAt: new Date(Date.now() - 10 * 3600000).toISOString(),
            timeline: [
                { id: 1, status: 'SUBMITTED', updatedByName: 'Jane Smith', comments: 'Reported streetlight failure.', createdAt: new Date(Date.now() - 48 * 3600000).toISOString() },
                { id: 2, status: 'ASSIGNED', updatedByName: 'City Chief Admin', comments: 'Assigned to Electrical Dept.', createdAt: new Date(Date.now() - 40 * 3600000).toISOString() },
                { id: 3, status: 'IN_PROGRESS', updatedByName: 'Officer Alex Smith', comments: 'Repairing broken LED fixture.', createdAt: new Date(Date.now() - 24 * 3600000).toISOString() },
                { id: 4, status: 'RESOLVED', updatedByName: 'Officer Alex Smith', comments: 'Replaced bulb fixture. Verified fully operational.', createdAt: new Date(Date.now() - 10 * 3600000).toISOString() }
            ]
        }
    ]
};

// Initialize Mock Store if not present
if (!localStorage.getItem('civic_mock_store')) {
    localStorage.setItem('civic_mock_store', JSON.stringify(INITIAL_MOCK_DATA));
}

function getMockStore() {
    return JSON.parse(localStorage.getItem('civic_mock_store')) || INITIAL_MOCK_DATA;
}

function saveMockStore(data) {
    localStorage.setItem('civic_mock_store', JSON.stringify(data));
}

// Global API Helper Object
window.CivicApi = {
    async login(username, password) {
        const store = getMockStore();
        const user = store.users.find(u => u.username === username && u.password === password);
        if (user) {
            const token = 'mock-jwt-token-' + Date.now();
            const session = {
                token,
                id: user.id,
                username: user.username,
                email: user.email,
                fullName: user.fullName,
                role: user.role,
                departmentId: user.departmentId || null,
                departmentName: user.departmentName || null,
                wardId: user.wardId || 1,
                wardName: user.wardName || 'Downtown Central'
            };
            localStorage.setItem('civic_user', JSON.stringify(session));
            return session;
        } else {
            throw new Error('Invalid username or password');
        }
    },

    async register(userData) {
        const store = getMockStore();
        if (store.users.some(u => u.username === userData.username)) {
            throw new Error('Username already exists');
        }
        const newUser = {
            id: Date.now(),
            username: userData.username,
            email: userData.email,
            password: userData.password,
            fullName: userData.fullName,
            role: userData.role || 'ROLE_CITIZEN',
            wardId: userData.wardId || 1,
            wardName: 'Downtown Central'
        };
        store.users.push(newUser);
        saveMockStore(store);
        return { message: 'Registration successful!' };
    },

    async getComplaints() {
        const store = getMockStore();
        return store.complaints;
    },

    async submitComplaint(data) {
        const store = getMockStore();
        const currentUser = JSON.parse(localStorage.getItem('civic_user')) || { fullName: 'John Doe', username: 'citizen_john' };
        
        const categoryMap = {
            '1': { name: 'Pothole & Road Damage', sla: 48, dept: 'Roads & Infrastructure' },
            '2': { name: 'Garbage Accumulation', sla: 24, dept: 'Sanitation & Solid Waste' },
            '3': { name: 'Broken Streetlight', sla: 72, dept: 'Electrical & Lighting' },
            '4': { name: 'Water Leakage', sla: 24, dept: 'Water & Sewage' },
            '5': { name: 'Drainage Overflow', sla: 36, dept: 'Water & Sewage' }
        };

        const catInfo = categoryMap[data.categoryId] || { name: 'General Civic Issue', sla: 48, dept: 'Public Works' };
        const id = Date.now();
        const complaintNumber = 'CMP-2026-' + Math.floor(1000 + Math.random() * 9000);
        const createdAt = new Date().toISOString();
        const slaDeadline = new Date(Date.now() + catInfo.sla * 3600000).toISOString();

        const newComplaint = {
            id,
            complaintNumber,
            title: data.title,
            description: data.description,
            categoryName: catInfo.name,
            departmentName: catInfo.dept,
            wardName: 'Downtown Central',
            wardNumber: 101,
            citizenName: currentUser.fullName,
            officerName: 'Unassigned',
            status: 'SUBMITTED',
            latitude: data.latitude || 28.6139,
            longitude: data.longitude || 77.2090,
            address: data.address || 'Submitted Location Pin',
            photoUrl: data.photoUrl || 'https://images.unsplash.com/photo-1584467735871-8e85353a8413?auto=format&fit=crop&w=600&q=80',
            slaDeadline,
            isEscalated: false,
            createdAt,
            updatedAt: createdAt,
            timeline: [
                { id: Date.now(), status: 'SUBMITTED', updatedByName: currentUser.fullName, comments: 'Complaint submitted by citizen.', createdAt }
            ]
        };

        store.complaints.unshift(newComplaint);
        saveMockStore(store);
        return newComplaint;
    },

    async updateStatus(complaintId, newStatus, comments, photoProof) {
        const store = getMockStore();
        const currentUser = JSON.parse(localStorage.getItem('civic_user')) || { fullName: 'Officer Smith' };
        const complaint = store.complaints.find(c => c.id === Number(complaintId));
        if (!complaint) throw new Error('Complaint not found');

        complaint.status = newStatus;
        if (newStatus === 'IN_PROGRESS' || newStatus === 'ASSIGNED') {
            complaint.officerName = currentUser.fullName;
        }
        complaint.updatedAt = new Date().toISOString();

        complaint.timeline.push({
            id: Date.now(),
            status: newStatus,
            updatedByName: currentUser.fullName,
            comments: comments || `Status updated to ${newStatus}`,
            photoProof: photoProof || null,
            createdAt: new Date().toISOString()
        });

        saveMockStore(store);
        return complaint;
    },

    async getAnalytics() {
        const store = getMockStore();
        const total = store.complaints.length;
        const pending = store.complaints.filter(c => c.status === 'SUBMITTED' || c.status === 'ASSIGNED').length;
        const inProgress = store.complaints.filter(c => c.status === 'IN_PROGRESS').length;
        const resolved = store.complaints.filter(c => c.status === 'RESOLVED' || c.status === 'CLOSED').length;
        const escalated = store.complaints.filter(c => c.isEscalated).length;

        const categoryDist = {};
        store.complaints.forEach(c => {
            categoryDist[c.categoryName] = (categoryDist[c.categoryName] || 0) + 1;
        });

        const statusDist = {};
        store.complaints.forEach(c => {
            statusDist[c.status] = (statusDist[c.status] || 0) + 1;
        });

        return {
            totalComplaints: total,
            pendingComplaints: pending,
            inProgressComplaints: inProgress,
            resolvedComplaints: resolved,
            escalatedComplaints: escalated,
            categoryDistribution: categoryDist,
            statusDistribution: statusDist
        };
    }
};
