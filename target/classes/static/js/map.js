/**
 * Civic Connect Leaflet GIS Map Manager (Feature 2A)
 */

window.CivicMap = {
    instance: null,
    pickerInstance: null,
    pickerMarker: null,

    init(containerId, centerLat = 28.6139, centerLng = 77.2090, zoom = 13) {
        const container = document.getElementById(containerId);
        if (!container) return null;

        if (this.instance) {
            this.instance.remove();
        }

        const map = L.map(containerId).setView([centerLat, centerLng], zoom);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors'
        }).addTo(map);

        this.instance = map;
        return map;
    },

    renderComplaintMarkers(map, complaints, onMarkerClick) {
        if (!map || !complaints) return;

        const getMarkerColor = (status) => {
            switch (status) {
                case 'SUBMITTED': return '#6c757d'; // Grey
                case 'ASSIGNED': return '#0dcaf0'; // Cyan
                case 'IN_PROGRESS': return '#ffc107'; // Yellow
                case 'RESOLVED': return '#198754'; // Green
                case 'CLOSED': return '#212529'; // Dark
                case 'REOPENED': return '#fd7e14'; // Orange
                default: return '#dc3545';
            }
        };

        complaints.forEach(c => {
            if (!c.latitude || !c.longitude) return;

            const color = getMarkerColor(c.status);
            const customIcon = L.divIcon({
                className: 'custom-map-pin',
                html: `<div style="background-color: ${color}; width: 18px; height: 18px; border-radius: 50%; border: 3px solid white; box-shadow: 0 2px 6px rgba(0,0,0,0.3);"></div>`,
                iconSize: [18, 18],
                iconAnchor: [9, 9]
            });

            const marker = L.marker([c.latitude, c.longitude], { icon: customIcon }).addTo(map);

            const popupContent = `
                <div style="min-width: 180px;">
                    <span class="badge bg-secondary mb-1">${c.complaintNumber}</span>
                    <h6 class="fw-bold mb-1">${c.title}</h6>
                    <p class="small text-muted mb-1">${c.categoryName}</p>
                    <p class="small mb-2"><strong>Status:</strong> <span style="color: ${color}; fw-bold">${c.status}</span></p>
                    <button class="btn btn-sm btn-outline-primary w-100" onclick="window.viewComplaintDetails(${c.id})">View Timeline</button>
                </div>
            `;

            marker.bindPopup(popupContent);
        });
    },

    initLocationPicker(containerId, onSelectLocation) {
        const container = document.getElementById(containerId);
        if (!container) return;

        if (this.pickerInstance) {
            this.pickerInstance.remove();
        }

        const defaultLat = 28.6139;
        const defaultLng = 77.2090;

        const map = L.map(containerId).setView([defaultLat, defaultLng], 14);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap'
        }).addTo(map);

        this.pickerInstance = map;

        const marker = L.marker([defaultLat, defaultLng], { draggable: true }).addTo(map);
        this.pickerMarker = marker;

        const updateCoords = (lat, lng) => {
            if (onSelectLocation) {
                onSelectLocation(lat, lng);
            }
        };

        updateCoords(defaultLat, defaultLng);

        marker.on('dragend', function (e) {
            const position = marker.getLatLng();
            updateCoords(position.lat, position.lng);
        });

        map.on('click', function (e) {
            marker.setLatLng(e.latlng);
            updateCoords(e.latlng.lat, e.latlng.lng);
        });

        setTimeout(() => map.invalidateSize(), 300);
    }
};
