import 'package:flutter/material.dart';
import '../core/theme/app_theme.dart';
import '../core/utils/date_formatter.dart';
import '../data/models/localization.dart';

class LocationHistoryItem extends StatelessWidget {
  final Localization localization;
  final VoidCallback? onTap;

  const LocationHistoryItem({
    super.key,
    required this.localization,
    this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.only(bottom: 10),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(16),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
                    decoration: BoxDecoration(
                      color: AppTheme.primaryColor.withValues(alpha: 0.15),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Text(
                      DateFormatter.formatDateTime(localization.timestamp),
                      style: const TextStyle(
                        color: AppTheme.primaryColor,
                        fontSize: 12,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                  ),
                  const Spacer(),
                  const Icon(
                    Icons.location_on,
                    color: AppTheme.primaryColor,
                    size: 18,
                  ),
                ],
              ),
              const SizedBox(height: 12),
              Text(
                localization.locationName,
                style: const TextStyle(
                  color: AppTheme.textPrimary,
                  fontSize: 16,
                  fontWeight: FontWeight.w600,
                ),
              ),
              const SizedBox(height: 8),
              Row(
                children: [
                  _buildInfoChip(
                    Icons.explore,
                    'Lat: ${localization.latitude.toStringAsFixed(4)}',
                  ),
                  const SizedBox(width: 8),
                  _buildInfoChip(
                    Icons.explore_outlined,
                    'Long: ${localization.longitude.toStringAsFixed(4)}',
                  ),
                ],
              ),
              if (localization.altitude != null) ...[
                const SizedBox(height: 8),
                _buildInfoChip(
                  Icons.height,
                  'Alt: ${localization.altitude!.toStringAsFixed(1)} m',
                ),
              ],
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildInfoChip(IconData icon, String text) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: AppTheme.cardDark,
        borderRadius: BorderRadius.circular(6),
        border: Border.all(
          color: AppTheme.textSecondary.withValues(alpha: 0.2),
        ),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon, size: 14, color: AppTheme.textSecondary),
          const SizedBox(width: 4),
          Text(
            text,
            style: const TextStyle(
              color: AppTheme.textSecondary,
              fontSize: 12,
            ),
          ),
        ],
      ),
    );
  }
}
