class ApiConstants {
  static const String baseUrl = 'http://10.0.2.2:8080';
  static const String apiUrl = '$baseUrl/api';

  static const String login = '$apiUrl/users/login';
  static const String users = '$apiUrl/users';
  static const String localizations = '$apiUrl/localizations';
  static const String managers = '$apiUrl/managers';
  static const String roles = '$apiUrl/roles';
}
