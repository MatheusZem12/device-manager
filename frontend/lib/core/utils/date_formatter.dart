import 'package:intl/intl.dart';

class DateFormatter {
  static final DateFormat _dateTime = DateFormat('dd/MM/yyyy HH:mm');
  static final DateFormat _date = DateFormat('dd/MM/yyyy');
  static final DateFormat _time = DateFormat('HH:mm');

  static String formatDateTime(String? isoDate) {
    if (isoDate == null) return '--';
    try {
      final date = DateTime.parse(isoDate);
      return _dateTime.format(date);
    } catch (_) {
      return isoDate;
    }
  }

  static String formatDate(String? isoDate) {
    if (isoDate == null) return '--';
    try {
      final date = DateTime.parse(isoDate);
      return _date.format(date);
    } catch (_) {
      return isoDate;
    }
  }

  static String formatTime(String? isoDate) {
    if (isoDate == null) return '--';
    try {
      final date = DateTime.parse(isoDate);
      return _time.format(date);
    } catch (_) {
      return isoDate;
    }
  }
}
