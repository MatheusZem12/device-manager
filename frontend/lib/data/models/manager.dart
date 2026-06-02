class Manager {
  final int? id;
  final int managerUserId;
  final int supervisedUserId;
  final String? managerUsername;
  final String? supervisedUsername;

  Manager({
    this.id,
    required this.managerUserId,
    required this.supervisedUserId,
    this.managerUsername,
    this.supervisedUsername,
  });

  factory Manager.fromJson(Map<String, dynamic> json) {
    return Manager(
      id: json['id'] as int?,
      managerUserId: json['managerUserId'] as int? ?? 0,
      supervisedUserId: json['supervisedUserId'] as int? ?? 0,
      managerUsername: json['managerUsername'] as String?,
      supervisedUsername: json['supervisedUsername'] as String?,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'managerUserId': managerUserId,
      'supervisedUserId': supervisedUserId,
      'managerUsername': managerUsername,
      'supervisedUsername': supervisedUsername,
    };
  }
}
