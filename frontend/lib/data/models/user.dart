class User {
  final int? id;
  final String username;
  final String email;
  final String? password;
  final String? deviceName;
  final List<String> roles;

  User({
    this.id,
    required this.username,
    required this.email,
    this.password,
    this.deviceName,
    this.roles = const [],
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'] as int?,
      username: json['username'] as String? ?? '',
      email: json['email'] as String? ?? '',
      password: json['password'] as String?,
      deviceName: json['deviceName'] as String?,
      roles: (json['roles'] as List<dynamic>?)
              ?.map((e) => e.toString())
              .toList() ??
          [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'email': email,
      if (password != null && password!.isNotEmpty) 'password': password,
      'deviceName': deviceName,
      'roles': roles,
    };
  }

  bool get isAdmin => roles.contains('ROLE_ADMIN');
  bool get isUser => roles.contains('ROLE_USER');

  User copyWith({
    int? id,
    String? username,
    String? email,
    String? password,
    String? deviceName,
    List<String>? roles,
  }) {
    return User(
      id: id ?? this.id,
      username: username ?? this.username,
      email: email ?? this.email,
      password: password ?? this.password,
      deviceName: deviceName ?? this.deviceName,
      roles: roles ?? this.roles,
    );
  }
}
