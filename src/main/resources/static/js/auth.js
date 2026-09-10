/**
 * Civic Connect Authentication & Session Helper
 */

window.CivicAuth = {
    getCurrentUser() {
        const userStr = localStorage.getItem('civic_user');
        return userStr ? JSON.parse(userStr) : null;
    },

    requireAuth(allowedRoles = []) {
        const user = this.getCurrentUser();
        if (!user) {
            window.location.href = 'index.html';
            return null;
        }
        if (allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
            alert('Access Denied: You do not have permission to view this page.');
            this.redirectUserByRole(user.role);
            return null;
        }
        return user;
    },

    redirectUserByRole(role) {
        if (role === 'ROLE_ADMIN') {
            window.location.href = 'admin-dashboard.html';
        } else if (role === 'ROLE_OFFICER') {
            window.location.href = 'officer-dashboard.html';
        } else {
            window.location.href = 'citizen-dashboard.html';
        }
    },

    logout() {
        localStorage.removeItem('civic_user');
        window.location.href = 'index.html';
    }
};
