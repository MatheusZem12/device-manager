import 'package:flutter/material.dart';
import '../data/models/localization.dart';
import '../data/models/user.dart';
import '../data/services/api_service.dart';

class LocationProvider extends ChangeNotifier {
  final ApiService _apiService = ApiService();
  List<Localization> _localizations = [];
  List<Localization> _userLocalizations = [];
  List<User> _supervisedUsers = [];
  bool _isLoading = false;
  String? _error;

  List<Localization> get localizations => _localizations;
  List<Localization> get userLocalizations => _userLocalizations;
  List<User> get supervisedUsers => _supervisedUsers;
  bool get isLoading => _isLoading;
  String? get error => _error;

  Future<void> loadAllLocalizations() async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      _localizations = await _apiService.getLocalizations();
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _error = 'Falha ao carregar localizações';
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadUserLocalizations(int userId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      _userLocalizations = await _apiService.getLocalizationsByUser(userId);
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _error = 'Falha ao carregar histórico';
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadSupervisedUsers(int managerUserId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final managers = await _apiService.getManagersByManagerUserId(managerUserId);
      _supervisedUsers = [];
      for (final manager in managers) {
        try {
          final user = await _apiService.getUserById(manager.supervisedUserId);
          _supervisedUsers.add(user);
        } catch (_) {
          // Skip if user not found
        }
      }
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _error = 'Falha ao carregar dispositivos';
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<bool> sendLocalization(Localization localization) async {
    try {
      await _apiService.createLocalization(localization);
      return true;
    } catch (e) {
      return false;
    }
  }

  void clearError() {
    _error = null;
    notifyListeners();
  }
}
