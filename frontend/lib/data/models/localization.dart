class Localization {
  final int? id;
  final double latitude;
  final double longitude;
  final double? altitude;
  final String? timestamp;
  final String? cep;
  final String? city;
  final String? state;
  final String? country;
  final int? userId;

  Localization({
    this.id,
    required this.latitude,
    required this.longitude,
    this.altitude,
    this.timestamp,
    this.cep,
    this.city,
    this.state,
    this.country,
    this.userId,
  });

  factory Localization.fromJson(Map<String, dynamic> json) {
    return Localization(
      id: json['id'] as int?,
      latitude: (json['latitude'] as num).toDouble(),
      longitude: (json['longitude'] as num).toDouble(),
      altitude: json['altitude'] != null ? (json['altitude'] as num).toDouble() : null,
      timestamp: json['timestamp'] as String?,
      cep: json['cep'] as String?,
      city: json['city'] as String?,
      state: json['state'] as String?,
      country: json['country'] as String?,
      userId: json['userId'] as int?,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'latitude': latitude,
      'longitude': longitude,
      'altitude': altitude,
      'timestamp': timestamp,
      'cep': cep,
      'city': city,
      'state': state,
      'country': country,
      'userId': userId,
    };
  }

  String get locationName {
    final c = city;
    final s = state;
    final parts = <String>[
      if (c != null && c.isNotEmpty) c,
      if (s != null && s.isNotEmpty) s,
    ];
    return parts.isNotEmpty ? parts.join(', ') : 'Localização desconhecida';
  }
}
