import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import '../core/theme/app_theme.dart';
import '../data/models/localization.dart';

class MapScreen extends StatelessWidget {
  final List<Localization>? localizations;
  final String? title;

  const MapScreen({super.key, this.localizations, this.title});

  @override
  Widget build(BuildContext context) {
    final locs = localizations ?? [];
    final hasData = locs.isNotEmpty;

    LatLng? center;
    if (hasData) {
      center = LatLng(locs.first.latitude, locs.first.longitude);
    } else {
      center = const LatLng(-23.5505, -46.6333); // São Paulo padrão
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(title ?? 'Mapa'),
      ),
      body: FlutterMap(
        options: MapOptions(
          initialCenter: center,
          initialZoom: hasData ? 13 : 4,
        ),
        children: [
          TileLayer(
            urlTemplate: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            subdomains: const ['a', 'b', 'c'],
            userAgentPackageName: 'br.com.zemtech.device_manager',
          ),
          if (hasData)
            MarkerLayer(
              markers: locs.map((loc) {
                return Marker(
                  point: LatLng(loc.latitude, loc.longitude),
                  width: 50,
                  height: 50,
                  child: Container(
                    decoration: BoxDecoration(
                      color: AppTheme.primaryColor,
                      shape: BoxShape.circle,
                      border: Border.all(color: Colors.white, width: 3),
                      boxShadow: [
                        BoxShadow(
                          color: AppTheme.primaryColor.withValues(alpha: 0.4),
                          blurRadius: 10,
                          spreadRadius: 2,
                        ),
                      ],
                    ),
                    child: const Icon(
                      Icons.location_on,
                      color: Colors.white,
                      size: 24,
                    ),
                  ),
                );
              }).toList(),
            ),
          if (hasData && locs.length > 1)
            PolylineLayer(
              polylines: [
                Polyline(
                  points: locs
                      .map((loc) => LatLng(loc.latitude, loc.longitude))
                      .toList(),
                  color: AppTheme.primaryColor.withValues(alpha: 0.7),
                  strokeWidth: 4,
                ),
              ],
            ),
          if (hasData)
            Positioned(
              left: 16,
              right: 16,
              bottom: 16,
              child: Card(
                child: Padding(
                  padding: const EdgeInsets.all(12),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Row(
                        children: [
                          const Icon(
                            Icons.info_outline,
                            color: AppTheme.primaryColor,
                            size: 18,
                          ),
                          const SizedBox(width: 8),
                          Expanded(
                            child: Text(
                              '${locs.length} ponto(s) no mapa',
                              style: const TextStyle(
                                color: AppTheme.textPrimary,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                          ),
                        ],
                      ),
                      if (locs.first.city != null) ...[
                        const SizedBox(height: 4),
                        Text(
                          'Local: ${locs.first.locationName}',
                          style: const TextStyle(
                            color: AppTheme.textSecondary,
                            fontSize: 13,
                          ),
                        ),
                      ],
                    ],
                  ),
                ),
              ),
            ),
        ],
      ),
    );
  }
}
