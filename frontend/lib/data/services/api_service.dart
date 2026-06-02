import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../core/constants/api_constants.dart';
import '../models/localization.dart';
import '../models/login_request.dart';
import '../models/login_response.dart';
import '../models/manager.dart';
import '../models/user.dart';

class ApiService {
  static final ApiService _instance = ApiService._internal();
  factory ApiService() => _instance;
  ApiService._internal();

  final Map<String, String> _headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };

  Future<http.Response> _get(String url) async {
    return http.get(Uri.parse(url), headers: _headers);
  }

  Future<http.Response> _post(String url, dynamic body) async {
    return http.post(
      Uri.parse(url),
      headers: _headers,
      body: jsonEncode(body),
    );
  }

  Future<http.Response> _put(String url, dynamic body) async {
    return http.put(
      Uri.parse(url),
      headers: _headers,
      body: jsonEncode(body),
    );
  }

  // Auth
  Future<LoginResponse> login(LoginRequest request) async {
    final response = await _post(ApiConstants.login, request.toJson());
    if (response.statusCode == 200) {
      return LoginResponse.fromJson(jsonDecode(response.body));
    }
    throw Exception('Falha no login: ${response.body}');
  }

  // Users
  Future<List<User>> getUsers() async {
    final response = await _get(ApiConstants.users);
    if (response.statusCode == 200) {
      final list = jsonDecode(response.body) as List<dynamic>;
      return list.map((e) => User.fromJson(e)).toList();
    }
    throw Exception('Falha ao carregar usuários');
  }

  Future<User> getUserById(int id) async {
    final response = await _get('${ApiConstants.users}/$id');
    if (response.statusCode == 200) {
      return User.fromJson(jsonDecode(response.body));
    }
    throw Exception('Falha ao carregar usuário');
  }

  Future<User> updateUser(int id, User user) async {
    final response = await _put('${ApiConstants.users}/$id', user.toJson());
    if (response.statusCode == 200) {
      return User.fromJson(jsonDecode(response.body));
    }
    throw Exception('Falha ao atualizar usuário');
  }

  // Localizations
  Future<List<Localization>> getLocalizations() async {
    final response = await _get(ApiConstants.localizations);
    if (response.statusCode == 200) {
      final list = jsonDecode(response.body) as List<dynamic>;
      return list.map((e) => Localization.fromJson(e)).toList();
    }
    throw Exception('Falha ao carregar localizações');
  }

  Future<List<Localization>> getLocalizationsByUser(int userId) async {
    final response = await _get('${ApiConstants.localizations}/user/$userId');
    if (response.statusCode == 200) {
      final list = jsonDecode(response.body) as List<dynamic>;
      return list.map((e) => Localization.fromJson(e)).toList();
    }
    throw Exception('Falha ao carregar localizações do usuário');
  }

  Future<Localization> createLocalization(Localization localization) async {
    final response = await _post(ApiConstants.localizations, localization.toJson());
    if (response.statusCode == 201 || response.statusCode == 200) {
      return Localization.fromJson(jsonDecode(response.body));
    }
    throw Exception('Falha ao criar localização');
  }

  // Managers
  Future<List<Manager>> getManagers() async {
    final response = await _get(ApiConstants.managers);
    if (response.statusCode == 200) {
      final list = jsonDecode(response.body) as List<dynamic>;
      return list.map((e) => Manager.fromJson(e)).toList();
    }
    throw Exception('Falha ao carregar managers');
  }

  Future<List<Manager>> getManagersByManagerUserId(int managerUserId) async {
    final response = await _get('${ApiConstants.managers}/manager-user/$managerUserId');
    if (response.statusCode == 200) {
      final list = jsonDecode(response.body) as List<dynamic>;
      return list.map((e) => Manager.fromJson(e)).toList();
    }
    throw Exception('Falha ao carregar dispositivos supervisionados');
  }
}
