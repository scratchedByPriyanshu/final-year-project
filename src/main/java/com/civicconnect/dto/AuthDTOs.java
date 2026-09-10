package com.civicconnect.dto;

public class AuthDTOs {

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private String fullName;
        private String phone;
        private String role;
        private Long departmentId;
        private Long wardId;

        public RegisterRequest() {}
        public RegisterRequest(String username, String email, String password, String fullName, String phone, String role, Long departmentId, Long wardId) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.fullName = fullName;
            this.phone = phone;
            this.role = role;
            this.departmentId = departmentId;
            this.wardId = wardId;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public Long getDepartmentId() { return departmentId; }
        public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

        public Long getWardId() { return wardId; }
        public void setWardId(Long wardId) { this.wardId = wardId; }
    }

    public static class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String role;
        private Long departmentId;
        private String departmentName;
        private Long wardId;
        private String wardName;

        public JwtResponse() {}
        public JwtResponse(String token, String type, Long id, String username, String email, String fullName, String role, Long departmentId, String departmentName, Long wardId, String wardName) {
            this.token = token;
            if (type != null) this.type = type;
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
            this.departmentId = departmentId;
            this.departmentName = departmentName;
            this.wardId = wardId;
            this.wardName = wardName;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public Long getDepartmentId() { return departmentId; }
        public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

        public Long getWardId() { return wardId; }
        public void setWardId(Long wardId) { this.wardId = wardId; }

        public String getWardName() { return wardName; }
        public void setWardName(String wardName) { this.wardName = wardName; }

        public static JwtResponseBuilder builder() { return new JwtResponseBuilder(); }

        public static class JwtResponseBuilder {
            private String token;
            private String type = "Bearer";
            private Long id;
            private String username;
            private String email;
            private String fullName;
            private String role;
            private Long departmentId;
            private String departmentName;
            private Long wardId;
            private String wardName;

            public JwtResponseBuilder token(String token) { this.token = token; return this; }
            public JwtResponseBuilder type(String type) { this.type = type; return this; }
            public JwtResponseBuilder id(Long id) { this.id = id; return this; }
            public JwtResponseBuilder username(String username) { this.username = username; return this; }
            public JwtResponseBuilder email(String email) { this.email = email; return this; }
            public JwtResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public JwtResponseBuilder role(String role) { this.role = role; return this; }
            public JwtResponseBuilder departmentId(Long departmentId) { this.departmentId = departmentId; return this; }
            public JwtResponseBuilder departmentName(String departmentName) { this.departmentName = departmentName; return this; }
            public JwtResponseBuilder wardId(Long wardId) { this.wardId = wardId; return this; }
            public JwtResponseBuilder wardName(String wardName) { this.wardName = wardName; return this; }

            public JwtResponse build() {
                return new JwtResponse(token, type, id, username, email, fullName, role, departmentId, departmentName, wardId, wardName);
            }
        }
    }
}
