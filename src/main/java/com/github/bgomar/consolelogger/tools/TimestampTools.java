package com.github.bgomar.consolelogger.tools;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TimestampTools {

    private static final DateTimeFormatter DATETIME_FORMAT_24H = DateTimeFormatter.ofPattern("yyyy-MM-dd E HH:mm:ss.SSS");

    public static TimestampFields toTimestampFields(long timestampAsLong, boolean isEpochSec) {
        Timestamp timestamp;
        if (isEpochSec) {
            timestamp = new Timestamp(timestampAsLong * 1000);
        } else {
            timestamp = new Timestamp(timestampAsLong);
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return new TimestampFields(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.getNano() / 1_000_000L
        );
    }

    public static long toTimestamp(TimestampFields timestampFields, String zoneIdAsStr, boolean isEpochSec) {
        if (isEpochSec) {
            return LocalDateTime.of(
                (int) timestampFields.year(),
                (int) timestampFields.month(),
                (int) timestampFields.day(),
                (int) timestampFields.hours(),
                (int) timestampFields.minutes(),
                (int) timestampFields.seconds(),
                0
            ).atZone(ZoneId.of(zoneIdAsStr)).toEpochSecond();
        } else {
            return LocalDateTime.of(
                (int) timestampFields.year(),
                (int) timestampFields.month(),
                (int) timestampFields.day(),
                (int) timestampFields.hours(),
                (int) timestampFields.minutes(),
                (int) timestampFields.seconds(),
                (int) timestampFields.millis() * 1_000_000
            ).atZone(ZoneId.of(zoneIdAsStr)).toInstant().toEpochMilli();
        }
    }

    public static long getNowAsTimestampMillis() {
        return Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())).getTime();
    }

    public static long getNowAsTimestampSec() {
        return getNowAsTimestampMillis() / 1000;
    }

    public static String getTimeStampAsHumanDatetime(long timestampAsLong, String zoneIdAsStr, boolean isEpochSec) {
        ZoneId zoneId = ZoneId.of(zoneIdAsStr);
        String zoneOffsetText = zoneId.getRules().getStandardOffset(Instant.now()).toString();

        Timestamp timestamp;
        if (isEpochSec) {
            timestamp = new Timestamp(timestampAsLong * 1000);
        } else {
            timestamp = new Timestamp(timestampAsLong);
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        String datetimeAsText = DATETIME_FORMAT_24H.format(localDateTime.atZone(ZoneId.systemDefault())
            .withZoneSameInstant(zoneId));
        String utcDatetimeAsText = DATETIME_FORMAT_24H.format(localDateTime.atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneId.of("UTC")));

        return zoneIdAsStr + " (GMT " + zoneOffsetText + ") Date and Time:\n" + datetimeAsText + "\n\nUTC Date and Time:\n" + utcDatetimeAsText;
    }

    public record TimestampFields(long year, long month, long day, long hours, long minutes, long seconds, long millis) {
    }

    //
    // TZ+flags list copied from https://github.com/bg-omar/ij-worldclock
    //

    private static final Map<String, String> zoneIdAndFlag = new LinkedHashMap<>();

    @SuppressWarnings("DuplicateBranchesInSwitch")
    public static Map<String, String> getAllAvailableZoneIdesAndFlags() {
        if (!zoneIdAndFlag.isEmpty()) {
            return zoneIdAndFlag;
        }

        // generated by ICU4J
        zoneIdAndFlag.put("Africa/Abidjan", "ci");
        zoneIdAndFlag.put("Africa/Accra", "gh");
        zoneIdAndFlag.put("Africa/Addis_Ababa", "et");
        zoneIdAndFlag.put("Africa/Algiers", "dz");
        zoneIdAndFlag.put("Africa/Asmera", "er");
        zoneIdAndFlag.put("Africa/Bamako", "ml");
        zoneIdAndFlag.put("Africa/Bangui", "cf");
        zoneIdAndFlag.put("Africa/Banjul", "gm");
        zoneIdAndFlag.put("Africa/Bissau", "gw");
        zoneIdAndFlag.put("Africa/Blantyre", "mw");
        zoneIdAndFlag.put("Africa/Brazzaville", "cg");
        zoneIdAndFlag.put("Africa/Bujumbura", "bi");
        zoneIdAndFlag.put("Africa/Cairo", "eg");
        zoneIdAndFlag.put("Africa/Casablanca", "ma");
        zoneIdAndFlag.put("Africa/Ceuta", "es");
        zoneIdAndFlag.put("Africa/Conakry", "gn");
        zoneIdAndFlag.put("Africa/Dakar", "sn");
        zoneIdAndFlag.put("Africa/Dar_es_Salaam", "tz");
        zoneIdAndFlag.put("Africa/Djibouti", "dj");
        zoneIdAndFlag.put("Africa/Douala", "cm");
        zoneIdAndFlag.put("Africa/El_Aaiun", "eh");
        zoneIdAndFlag.put("Africa/Freetown", "sl");
        zoneIdAndFlag.put("Africa/Gaborone", "bw");
        zoneIdAndFlag.put("Africa/Harare", "zw");
        zoneIdAndFlag.put("Africa/Johannesburg", "za");
        zoneIdAndFlag.put("Africa/Kampala", "ug");
        zoneIdAndFlag.put("Africa/Khartoum", "sd");
        zoneIdAndFlag.put("Africa/Kigali", "rw");
        zoneIdAndFlag.put("Africa/Kinshasa", "cd");
        zoneIdAndFlag.put("Africa/Lagos", "ng");
        zoneIdAndFlag.put("Africa/Libreville", "ga");
        zoneIdAndFlag.put("Africa/Lome", "tg");
        zoneIdAndFlag.put("Africa/Luanda", "ao");
        zoneIdAndFlag.put("Africa/Lubumbashi", "cd");
        zoneIdAndFlag.put("Africa/Lusaka", "zm");
        zoneIdAndFlag.put("Africa/Malabo", "gq");
        zoneIdAndFlag.put("Africa/Maputo", "mz");
        zoneIdAndFlag.put("Africa/Maseru", "ls");
        zoneIdAndFlag.put("Africa/Mbabane", "sz");
        zoneIdAndFlag.put("Africa/Mogadishu", "so");
        zoneIdAndFlag.put("Africa/Monrovia", "lr");
        zoneIdAndFlag.put("Africa/Nairobi", "ke");
        zoneIdAndFlag.put("Africa/Ndjamena", "td");
        zoneIdAndFlag.put("Africa/Niamey", "ne");
        zoneIdAndFlag.put("Africa/Nouakchott", "mr");
        zoneIdAndFlag.put("Africa/Ouagadougou", "bf");
        zoneIdAndFlag.put("Africa/Porto-Novo", "bj");
        zoneIdAndFlag.put("Africa/Sao_Tome", "st");
        zoneIdAndFlag.put("Africa/Timbuktu", "ml");
        zoneIdAndFlag.put("Africa/Tripoli", "ly");
        zoneIdAndFlag.put("Africa/Tunis", "tn");
        zoneIdAndFlag.put("Africa/Windhoek", "na");
        zoneIdAndFlag.put("America/Adak", "us");
        zoneIdAndFlag.put("America/Anchorage", "us");
        zoneIdAndFlag.put("America/Anguilla", "ai");
        zoneIdAndFlag.put("America/Antigua", "ag");
        zoneIdAndFlag.put("America/Araguaina", "br");
        zoneIdAndFlag.put("America/Aruba", "aw");
        zoneIdAndFlag.put("America/Asuncion", "py");
        zoneIdAndFlag.put("America/Barbados", "bb");
        zoneIdAndFlag.put("America/Belem", "br");
        zoneIdAndFlag.put("America/Belize", "bz");
        zoneIdAndFlag.put("America/Boa_Vista", "br");
        zoneIdAndFlag.put("America/Bogota", "co");
        zoneIdAndFlag.put("America/Boise", "us");
        zoneIdAndFlag.put("America/Buenos_Aires", "ar");
        zoneIdAndFlag.put("America/Cambridge_Bay", "ca");
        zoneIdAndFlag.put("America/Cancun", "mx");
        zoneIdAndFlag.put("America/Caracas", "ve");
        zoneIdAndFlag.put("America/Catamarca", "ar");
        zoneIdAndFlag.put("America/Cayenne", "gf");
        zoneIdAndFlag.put("America/Cayman", "ky");
        zoneIdAndFlag.put("America/Chicago", "us");
        zoneIdAndFlag.put("America/Chihuahua", "mx");
        zoneIdAndFlag.put("America/Cordoba", "ar");
        zoneIdAndFlag.put("America/Costa_Rica", "cr");
        zoneIdAndFlag.put("America/Cuiaba", "br");
        zoneIdAndFlag.put("America/Danmarkshavn", "gl");
        zoneIdAndFlag.put("America/Dawson", "ca");
        zoneIdAndFlag.put("America/Dawson_Creek", "ca");
        zoneIdAndFlag.put("America/Denver", "us");
        zoneIdAndFlag.put("America/Detroit", "us");
        zoneIdAndFlag.put("America/Dominica", "dm");
        zoneIdAndFlag.put("America/Edmonton", "ca");
        zoneIdAndFlag.put("America/Eirunepe", "br");
        zoneIdAndFlag.put("America/El_Salvador", "sv");
        zoneIdAndFlag.put("America/Fortaleza", "br");
        zoneIdAndFlag.put("America/Glace_Bay", "ca");
        zoneIdAndFlag.put("America/Godthab", "gl");
        zoneIdAndFlag.put("America/Goose_Bay", "ca");
        zoneIdAndFlag.put("America/Grand_Turk", "tc");
        zoneIdAndFlag.put("America/Grenada", "gd");
        zoneIdAndFlag.put("America/Guadeloupe", "gp");
        zoneIdAndFlag.put("America/Guatemala", "gt");
        zoneIdAndFlag.put("America/Guayaquil", "ec");
        zoneIdAndFlag.put("America/Guyana", "gy");
        zoneIdAndFlag.put("America/Halifax", "ca");
        zoneIdAndFlag.put("America/Havana", "cu");
        zoneIdAndFlag.put("America/Hermosillo", "mx");
        zoneIdAndFlag.put("America/Indiana/Knox", "us");
        zoneIdAndFlag.put("America/Indiana/Marengo", "us");
        zoneIdAndFlag.put("America/Indiana/Vevay", "us");
        zoneIdAndFlag.put("America/Indianapolis", "us");
        zoneIdAndFlag.put("America/Inuvik", "ca");
        zoneIdAndFlag.put("America/Iqaluit", "ca");
        zoneIdAndFlag.put("America/Jamaica", "jm");
        zoneIdAndFlag.put("America/Jujuy", "ar");
        zoneIdAndFlag.put("America/Juneau", "us");
        zoneIdAndFlag.put("America/Kentucky/Monticello", "us");
        zoneIdAndFlag.put("America/La_Paz", "bo");
        zoneIdAndFlag.put("America/Lima", "pe");
        zoneIdAndFlag.put("America/Los_Angeles", "us");
        zoneIdAndFlag.put("America/Louisville", "us");
        zoneIdAndFlag.put("America/Maceio", "br");
        zoneIdAndFlag.put("America/Managua", "ni");
        zoneIdAndFlag.put("America/Manaus", "br");
        zoneIdAndFlag.put("America/Martinique", "mq");
        zoneIdAndFlag.put("America/Mazatlan", "mx");
        zoneIdAndFlag.put("America/Mendoza", "ar");
        zoneIdAndFlag.put("America/Menominee", "us");
        zoneIdAndFlag.put("America/Merida", "mx");
        zoneIdAndFlag.put("America/Mexico_City", "mx");
        zoneIdAndFlag.put("America/Miquelon", "pm");
        zoneIdAndFlag.put("America/Monterrey", "mx");
        zoneIdAndFlag.put("America/Montevideo", "uy");
        zoneIdAndFlag.put("America/Montreal", "ca");
        zoneIdAndFlag.put("America/Montserrat", "ms");
        zoneIdAndFlag.put("America/Nassau", "bs");
        zoneIdAndFlag.put("America/New_York", "us");
        zoneIdAndFlag.put("America/Nipigon", "ca");
        zoneIdAndFlag.put("America/Nome", "us");
        zoneIdAndFlag.put("America/Noronha", "br");
        zoneIdAndFlag.put("America/North_Dakota/Center", "us");
        zoneIdAndFlag.put("America/Panama", "pa");
        zoneIdAndFlag.put("America/Pangnirtung", "ca");
        zoneIdAndFlag.put("America/Paramaribo", "sr");
        zoneIdAndFlag.put("America/Phoenix", "us");
        zoneIdAndFlag.put("America/Port-au-Prince", "ht");
        zoneIdAndFlag.put("America/Port_of_Spain", "tt");
        zoneIdAndFlag.put("America/Porto_Velho", "br");
        zoneIdAndFlag.put("America/Puerto_Rico", "pr");
        zoneIdAndFlag.put("America/Rainy_River", "ca");
        zoneIdAndFlag.put("America/Rankin_Inlet", "ca");
        zoneIdAndFlag.put("America/Recife", "br");
        zoneIdAndFlag.put("America/Regina", "ca");
        zoneIdAndFlag.put("America/Rio_Branco", "br");
        zoneIdAndFlag.put("America/Santiago", "cl");
        zoneIdAndFlag.put("America/Santo_Domingo", "do");
        zoneIdAndFlag.put("America/Sao_Paulo", "br");
        zoneIdAndFlag.put("America/Scoresbysund", "gl");
        zoneIdAndFlag.put("America/St_Johns", "ca");
        zoneIdAndFlag.put("America/St_Kitts", "kn");
        zoneIdAndFlag.put("America/St_Lucia", "lc");
        zoneIdAndFlag.put("America/St_Thomas", "vi");
        zoneIdAndFlag.put("America/St_Vincent", "vc");
        zoneIdAndFlag.put("America/Swift_Current", "ca");
        zoneIdAndFlag.put("America/Tegucigalpa", "hn");
        zoneIdAndFlag.put("America/Thule", "gl");
        zoneIdAndFlag.put("America/Thunder_Bay", "ca");
        zoneIdAndFlag.put("America/Tijuana", "mx");
        zoneIdAndFlag.put("America/Tortola", "vg");
        zoneIdAndFlag.put("America/Vancouver", "ca");
        zoneIdAndFlag.put("America/Whitehorse", "ca");
        zoneIdAndFlag.put("America/Winnipeg", "ca");
        zoneIdAndFlag.put("America/Yakutat", "us");
        zoneIdAndFlag.put("America/Yellowknife", "ca");
        zoneIdAndFlag.put("Antarctica/Casey", "aq");
        zoneIdAndFlag.put("Antarctica/Davis", "aq");
        zoneIdAndFlag.put("Antarctica/DumontDUrville", "aq");
        zoneIdAndFlag.put("Antarctica/Mawson", "aq");
        zoneIdAndFlag.put("Antarctica/McMurdo", "aq");
        zoneIdAndFlag.put("Antarctica/Palmer", "aq");
        zoneIdAndFlag.put("Antarctica/Rothera", "aq");
        zoneIdAndFlag.put("Antarctica/Syowa", "aq");
        zoneIdAndFlag.put("Antarctica/Vostok", "aq");
        zoneIdAndFlag.put("Arctic/Longyearbyen", "sj");
        zoneIdAndFlag.put("Asia/Aden", "ye");
        zoneIdAndFlag.put("Asia/Almaty", "kz");
        zoneIdAndFlag.put("Asia/Amman", "jo");
        zoneIdAndFlag.put("Asia/Anadyr", "ru");
        zoneIdAndFlag.put("Asia/Aqtau", "kz");
        zoneIdAndFlag.put("Asia/Aqtobe", "kz");
        zoneIdAndFlag.put("Asia/Ashgabat", "tm");
        zoneIdAndFlag.put("Asia/Baghdad", "iq");
        zoneIdAndFlag.put("Asia/Bahrain", "bh");
        zoneIdAndFlag.put("Asia/Baku", "az");
        zoneIdAndFlag.put("Asia/Bangkok", "th");
        zoneIdAndFlag.put("Asia/Beirut", "lb");
        zoneIdAndFlag.put("Asia/Bishkek", "kg");
        zoneIdAndFlag.put("Asia/Brunei", "bn");
        zoneIdAndFlag.put("Asia/Calcutta", "in");
        zoneIdAndFlag.put("Asia/Choibalsan", "mn");
        zoneIdAndFlag.put("Asia/Chongqing", "cn");
        zoneIdAndFlag.put("Asia/Colombo", "lk");
        zoneIdAndFlag.put("Asia/Damascus", "sy");
        zoneIdAndFlag.put("Asia/Dhaka", "bd");
        zoneIdAndFlag.put("Asia/Dili", "tl");
        zoneIdAndFlag.put("Asia/Dubai", "ae");
        zoneIdAndFlag.put("Asia/Dushanbe", "tj");
        zoneIdAndFlag.put("Asia/Gaza", "ps");
        zoneIdAndFlag.put("Asia/Harbin", "cn");
        zoneIdAndFlag.put("Asia/Hong_Kong", "hk");
        zoneIdAndFlag.put("Asia/Hovd", "mn");
        zoneIdAndFlag.put("Asia/Irkutsk", "ru");
        zoneIdAndFlag.put("Asia/Jakarta", "id");
        zoneIdAndFlag.put("Asia/Jayapura", "id");
        zoneIdAndFlag.put("Asia/Jerusalem", "il");
        zoneIdAndFlag.put("Asia/Kabul", "af");
        zoneIdAndFlag.put("Asia/Kamchatka", "ru");
        zoneIdAndFlag.put("Asia/Karachi", "pk");
        zoneIdAndFlag.put("Asia/Kashgar", "cn");
        zoneIdAndFlag.put("Asia/Katmandu", "np");
        zoneIdAndFlag.put("Asia/Krasnoyarsk", "ru");
        zoneIdAndFlag.put("Asia/Kuala_Lumpur", "my");
        zoneIdAndFlag.put("Asia/Kuching", "my");
        zoneIdAndFlag.put("Asia/Kuwait", "kw");
        zoneIdAndFlag.put("Asia/Macau", "mo");
        zoneIdAndFlag.put("Asia/Magadan", "ru");
        zoneIdAndFlag.put("Asia/Makassar", "id");
        zoneIdAndFlag.put("Asia/Manila", "ph");
        zoneIdAndFlag.put("Asia/Muscat", "om");
        zoneIdAndFlag.put("Asia/Nicosia", "cy");
        zoneIdAndFlag.put("Asia/Novosibirsk", "ru");
        zoneIdAndFlag.put("Asia/Omsk", "ru");
        zoneIdAndFlag.put("Asia/Oral", "kz");
        zoneIdAndFlag.put("Asia/Phnom_Penh", "kh");
        zoneIdAndFlag.put("Asia/Pontianak", "id");
        zoneIdAndFlag.put("Asia/Pyongyang", "kp");
        zoneIdAndFlag.put("Asia/Qatar", "qa");
        zoneIdAndFlag.put("Asia/Qyzylorda", "kz");
        zoneIdAndFlag.put("Asia/Rangoon", "mm");
        zoneIdAndFlag.put("Asia/Riyadh", "sa");
        zoneIdAndFlag.put("Asia/Saigon", "vn");
        zoneIdAndFlag.put("Asia/Sakhalin", "ru");
        zoneIdAndFlag.put("Asia/Samarkand", "uz");
        zoneIdAndFlag.put("Asia/Seoul", "kr");
        zoneIdAndFlag.put("Asia/Shanghai", "cn");
        zoneIdAndFlag.put("Asia/Singapore", "sg");
        zoneIdAndFlag.put("Asia/Taipei", "tw");
        zoneIdAndFlag.put("Asia/Tashkent", "uz");
        zoneIdAndFlag.put("Asia/Tbilisi", "ge");
        zoneIdAndFlag.put("Asia/Tehran", "ir");
        zoneIdAndFlag.put("Asia/Thimphu", "bt");
        zoneIdAndFlag.put("Asia/Tokyo", "jp");
        zoneIdAndFlag.put("Asia/Ulaanbaatar", "mn");
        zoneIdAndFlag.put("Asia/Urumqi", "cn");
        zoneIdAndFlag.put("Asia/Vientiane", "la");
        zoneIdAndFlag.put("Asia/Vladivostok", "ru");
        zoneIdAndFlag.put("Asia/Yakutsk", "ru");
        zoneIdAndFlag.put("Asia/Yekaterinburg", "ru");
        zoneIdAndFlag.put("Asia/Yerevan", "am");
        zoneIdAndFlag.put("Atlantic/Azores", "pt");
        zoneIdAndFlag.put("Atlantic/Bermuda", "bm");
        zoneIdAndFlag.put("Atlantic/Canary", "es");
        zoneIdAndFlag.put("Atlantic/Cape_Verde", "cv");
        zoneIdAndFlag.put("Atlantic/Faeroe", "fo");
        zoneIdAndFlag.put("Atlantic/Jan_Mayen", "sj");
        zoneIdAndFlag.put("Atlantic/Madeira", "pt");
        zoneIdAndFlag.put("Atlantic/Reykjavik", "is");
        zoneIdAndFlag.put("Atlantic/South_Georgia", "gs");
        zoneIdAndFlag.put("Atlantic/St_Helena", "sh");
        zoneIdAndFlag.put("Atlantic/Stanley", "fk");
        zoneIdAndFlag.put("Australia/Adelaide", "au");
        zoneIdAndFlag.put("Australia/Brisbane", "au");
        zoneIdAndFlag.put("Australia/Broken_Hill", "au");
        zoneIdAndFlag.put("Australia/Darwin", "au");
        zoneIdAndFlag.put("Australia/Hobart", "au");
        zoneIdAndFlag.put("Australia/Lindeman", "au");
        zoneIdAndFlag.put("Australia/Lord_Howe", "au");
        zoneIdAndFlag.put("Australia/Melbourne", "au");
        zoneIdAndFlag.put("Australia/Perth", "au");
        zoneIdAndFlag.put("Australia/Sydney", "au");
        zoneIdAndFlag.put("Europe/Amsterdam", "nl");
        zoneIdAndFlag.put("Europe/Andorra", "ad");
        zoneIdAndFlag.put("Europe/Athens", "gr");
        zoneIdAndFlag.put("Europe/Belfast", "gb");
        zoneIdAndFlag.put("Europe/Berlin", "de");
        zoneIdAndFlag.put("Europe/Bratislava", "sk");
        zoneIdAndFlag.put("Europe/Brussels", "be");
        zoneIdAndFlag.put("Europe/Bucharest", "ro");
        zoneIdAndFlag.put("Europe/Budapest", "hu");
        zoneIdAndFlag.put("Europe/Chisinau", "md");
        zoneIdAndFlag.put("Europe/Copenhagen", "dk");
        zoneIdAndFlag.put("Europe/Dublin", "ie");
        zoneIdAndFlag.put("Europe/Gibraltar", "gi");
        zoneIdAndFlag.put("Europe/Helsinki", "fi");
        zoneIdAndFlag.put("Europe/Istanbul", "tr");
        zoneIdAndFlag.put("Europe/Kaliningrad", "ru");
        zoneIdAndFlag.put("Europe/Kiev", "ua");
        zoneIdAndFlag.put("Europe/Lisbon", "pt");
        zoneIdAndFlag.put("Europe/Ljubljana", "si");
        zoneIdAndFlag.put("Europe/London", "gb");
        zoneIdAndFlag.put("Europe/Luxembourg", "lu");
        zoneIdAndFlag.put("Europe/Madrid", "es");
        zoneIdAndFlag.put("Europe/Malta", "mt");
        zoneIdAndFlag.put("Europe/Minsk", "by");
        zoneIdAndFlag.put("Europe/Monaco", "mc");
        zoneIdAndFlag.put("Europe/Moscow", "ru");
        zoneIdAndFlag.put("Europe/Oslo", "no");
        zoneIdAndFlag.put("Europe/Paris", "fr");
        zoneIdAndFlag.put("Europe/Prague", "cz");
        zoneIdAndFlag.put("Europe/Riga", "lv");
        zoneIdAndFlag.put("Europe/Rome", "it");
        zoneIdAndFlag.put("Europe/Samara", "ru");
        zoneIdAndFlag.put("Europe/San_Marino", "sm");
        zoneIdAndFlag.put("Europe/Sarajevo", "ba");
        zoneIdAndFlag.put("Europe/Simferopol", "ua");
        zoneIdAndFlag.put("Europe/Skopje", "mk");
        zoneIdAndFlag.put("Europe/Sofia", "bg");
        zoneIdAndFlag.put("Europe/Stockholm", "se");
        zoneIdAndFlag.put("Europe/Tallinn", "ee");
        zoneIdAndFlag.put("Europe/Tirane", "al");
        zoneIdAndFlag.put("Europe/Uzhgorod", "ua");
        zoneIdAndFlag.put("Europe/Vaduz", "li");
        zoneIdAndFlag.put("Europe/Vatican", "va");
        zoneIdAndFlag.put("Europe/Vienna", "at");
        zoneIdAndFlag.put("Europe/Vilnius", "lt");
        zoneIdAndFlag.put("Europe/Warsaw", "pl");
        zoneIdAndFlag.put("Europe/Zagreb", "hr");
        zoneIdAndFlag.put("Europe/Zaporozhye", "ua");
        zoneIdAndFlag.put("Europe/Zurich", "ch");
        zoneIdAndFlag.put("Indian/Antananarivo", "mg");
        zoneIdAndFlag.put("Indian/Chagos", "io");
        zoneIdAndFlag.put("Indian/Christmas", "cx");
        zoneIdAndFlag.put("Indian/Cocos", "cc");
        zoneIdAndFlag.put("Indian/Comoro", "km");
        zoneIdAndFlag.put("Indian/Kerguelen", "tf");
        zoneIdAndFlag.put("Indian/Mahe", "sc");
        zoneIdAndFlag.put("Indian/Maldives", "mv");
        zoneIdAndFlag.put("Indian/Mauritius", "mu");
        zoneIdAndFlag.put("Indian/Mayotte", "yt");
        zoneIdAndFlag.put("Indian/Reunion", "re");
        zoneIdAndFlag.put("Pacific/Apia", "ws");
        zoneIdAndFlag.put("Pacific/Auckland", "nz");
        zoneIdAndFlag.put("Pacific/Chatham", "nz");
        zoneIdAndFlag.put("Pacific/Easter", "cl");
        zoneIdAndFlag.put("Pacific/Efate", "vu");
        zoneIdAndFlag.put("Pacific/Enderbury", "ki");
        zoneIdAndFlag.put("Pacific/Fakaofo", "tk");
        zoneIdAndFlag.put("Pacific/Fiji", "fj");
        zoneIdAndFlag.put("Pacific/Funafuti", "tv");
        zoneIdAndFlag.put("Pacific/Galapagos", "ec");
        zoneIdAndFlag.put("Pacific/Gambier", "pf");
        zoneIdAndFlag.put("Pacific/Guadalcanal", "sb");
        zoneIdAndFlag.put("Pacific/Guam", "gu");
        zoneIdAndFlag.put("Pacific/Honolulu", "us");
        zoneIdAndFlag.put("Pacific/Johnston", "um");
        zoneIdAndFlag.put("Pacific/Kiritimati", "ki");
        zoneIdAndFlag.put("Pacific/Kosrae", "fm");
        zoneIdAndFlag.put("Pacific/Kwajalein", "mh");
        zoneIdAndFlag.put("Pacific/Majuro", "mh");
        zoneIdAndFlag.put("Pacific/Marquesas", "pf");
        zoneIdAndFlag.put("Pacific/Midway", "um");
        zoneIdAndFlag.put("Pacific/Nauru", "nr");
        zoneIdAndFlag.put("Pacific/Niue", "nu");
        zoneIdAndFlag.put("Pacific/Norfolk", "nf");
        zoneIdAndFlag.put("Pacific/Noumea", "nc");
        zoneIdAndFlag.put("Pacific/Pago_Pago", "as");
        zoneIdAndFlag.put("Pacific/Palau", "pw");
        zoneIdAndFlag.put("Pacific/Pitcairn", "pn");
        zoneIdAndFlag.put("Pacific/Ponape", "fm");
        zoneIdAndFlag.put("Pacific/Port_Moresby", "pg");
        zoneIdAndFlag.put("Pacific/Rarotonga", "ck");
        zoneIdAndFlag.put("Pacific/Saipan", "mp");
        zoneIdAndFlag.put("Pacific/Tahiti", "pf");
        zoneIdAndFlag.put("Pacific/Tarawa", "ki");
        zoneIdAndFlag.put("Pacific/Tongatapu", "to");
        zoneIdAndFlag.put("Pacific/Truk", "fm");
        zoneIdAndFlag.put("Pacific/Wake", "um");
        zoneIdAndFlag.put("Pacific/Wallis", "wf");
        zoneIdAndFlag.put("Pacific/Yap", "fm");

        // some zoneIds are not supported by running JVM: remove these unsupported zoneIds
        Set<String> unsupportedZoneIds = new HashSet<>();
        Set<String> supportedJVMZoneIds = ZoneId.getAvailableZoneIds();
        zoneIdAndFlag.keySet().forEach(zoneId -> {
            if (!supportedJVMZoneIds.contains(zoneId)) {
                unsupportedZoneIds.add(zoneId);
            }
        });
        unsupportedZoneIds.forEach(zoneIdAndFlag::remove);

        supportedJVMZoneIds.forEach(jvmZoneId -> {
            if (!zoneIdAndFlag.containsKey(jvmZoneId)) {

                // add zoneIds not supported by UCU4J. Generic associations
                if (jvmZoneId.startsWith("America/Argentina/")) {
                    zoneIdAndFlag.put(jvmZoneId, "ar");
                } else if (jvmZoneId.startsWith("America/Indiana/")) {
                    zoneIdAndFlag.put(jvmZoneId, "us");
                } else if (jvmZoneId.startsWith("America/North_Dakota/")) {
                    zoneIdAndFlag.put(jvmZoneId, "us");
                } else if (jvmZoneId.startsWith("Canada/")) {
                    zoneIdAndFlag.put(jvmZoneId, "ca");
                } else if (jvmZoneId.startsWith("Chile/")) {
                    zoneIdAndFlag.put(jvmZoneId, "cl");
                } else if (jvmZoneId.startsWith("Mexico/")) {
                    zoneIdAndFlag.put(jvmZoneId, "mx");
                } else if (jvmZoneId.startsWith("US/")) {
                    zoneIdAndFlag.put(jvmZoneId, "us");
                } else if (jvmZoneId.startsWith("Australia/")) {
                    zoneIdAndFlag.put(jvmZoneId, "au");
                } else if (jvmZoneId.startsWith("Brazil/")) {
                    zoneIdAndFlag.put(jvmZoneId, "br");
                }

                // add zoneIds not supported by UCU4J. Source: https://github.com/eggert/tz/blob/main/zone.tab
                switch (jvmZoneId) {
                    case "Africa/Asmara" -> zoneIdAndFlag.put(jvmZoneId, "er");
                    case "Africa/Juba" -> zoneIdAndFlag.put(jvmZoneId, "ss");
                    case "America/Atikokan" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "America/Bahia" -> zoneIdAndFlag.put(jvmZoneId, "br");
                    case "America/Bahia_Banderas" -> zoneIdAndFlag.put(jvmZoneId, "mx");
                    case "America/Blanc-Sablon" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "America/Campo_Grande" -> zoneIdAndFlag.put(jvmZoneId, "br");
                    case "America/Creston" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "America/Curacao" -> zoneIdAndFlag.put(jvmZoneId, "cw");
                    case "America/Fort_Nelson" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "America/Kentucky/Louisville" -> zoneIdAndFlag.put(jvmZoneId, "us");
                    case "America/Knox_IN" -> zoneIdAndFlag.put(jvmZoneId, "xx");
                    case "America/Kralendijk" -> zoneIdAndFlag.put(jvmZoneId, "bq");
                    case "America/Lower_Princes" -> zoneIdAndFlag.put(jvmZoneId, "sx");
                    case "America/Marigot" -> zoneIdAndFlag.put(jvmZoneId, "mf");
                    case "America/Matamoros" -> zoneIdAndFlag.put(jvmZoneId, "mx");
                    case "America/Metlakatla" -> zoneIdAndFlag.put(jvmZoneId, "us");
                    case "America/Moncton" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "America/Nuuk" -> zoneIdAndFlag.put(jvmZoneId, "gl");
                    case "America/Ojinaga" -> zoneIdAndFlag.put(jvmZoneId, "mx");
                    case "America/Punta_Arenas" -> zoneIdAndFlag.put(jvmZoneId, "cl");
                    case "America/Resolute" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "America/Santarem" -> zoneIdAndFlag.put(jvmZoneId, "br");
                    case "America/Sitka" -> zoneIdAndFlag.put(jvmZoneId, "us");
                    case "America/St_Barthelemy" -> zoneIdAndFlag.put(jvmZoneId, "bl");
                    case "America/Toronto" -> zoneIdAndFlag.put(jvmZoneId, "ca");
                    case "Antarctica/Macquarie" -> zoneIdAndFlag.put(jvmZoneId, "au");
                    case "Antarctica/South_Pole" -> zoneIdAndFlag.put(jvmZoneId, "aq");
                    case "Antarctica/Troll" -> zoneIdAndFlag.put(jvmZoneId, "aq");
                    case "Asia/Atyrau" -> zoneIdAndFlag.put(jvmZoneId, "kz");
                    case "Asia/Barnaul" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Chita" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Famagusta" -> zoneIdAndFlag.put(jvmZoneId, "cy");
                    case "Asia/Hebron" -> zoneIdAndFlag.put(jvmZoneId, "ps");
                    case "Asia/Ho_Chi_Minh" -> zoneIdAndFlag.put(jvmZoneId, "vn");
                    case "Asia/Kathmandu" -> zoneIdAndFlag.put(jvmZoneId, "np");
                    case "Asia/Khandyga" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Kolkata" -> zoneIdAndFlag.put(jvmZoneId, "in");
                    case "Asia/Macao" -> zoneIdAndFlag.put(jvmZoneId, "mo");
                    case "Asia/Novokuznetsk" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Qostanay" -> zoneIdAndFlag.put(jvmZoneId, "kz");
                    case "Asia/Srednekolymsk" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Tel_Aviv" -> zoneIdAndFlag.put(jvmZoneId, "il");
                    case "Asia/Tomsk" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Ust-Nera" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Asia/Yangon" -> zoneIdAndFlag.put(jvmZoneId, "mm");
                    case "Atlantic/Faroe" -> zoneIdAndFlag.put(jvmZoneId, "fo");
                    case "Europe/Astrakhan" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Europe/Belgrade" -> zoneIdAndFlag.put(jvmZoneId, "rs");
                    case "Europe/Busingen" -> zoneIdAndFlag.put(jvmZoneId, "de");
                    case "Europe/Guernsey" -> zoneIdAndFlag.put(jvmZoneId, "gg");
                    case "Europe/Isle_of_Man" -> zoneIdAndFlag.put(jvmZoneId, "im");
                    case "Europe/Jersey" -> zoneIdAndFlag.put(jvmZoneId, "je");
                    case "Europe/Kirov" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Europe/Mariehamn" -> zoneIdAndFlag.put(jvmZoneId, "ax");
                    case "Europe/Podgorica" -> zoneIdAndFlag.put(jvmZoneId, "me");
                    case "Europe/Saratov" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Europe/Ulyanovsk" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Europe/Volgograd" -> zoneIdAndFlag.put(jvmZoneId, "ru");
                    case "Pacific/Bougainville" -> zoneIdAndFlag.put(jvmZoneId, "pg");
                    case "Pacific/Chuuk" -> zoneIdAndFlag.put(jvmZoneId, "fm");
                    case "Pacific/Kanton" -> zoneIdAndFlag.put(jvmZoneId, "ki");
                    case "Pacific/Pohnpei" -> zoneIdAndFlag.put(jvmZoneId, "fm");
                }
            }
        });

        zoneIdAndFlag.put("CST", null);
        zoneIdAndFlag.put("EST", null);
        zoneIdAndFlag.put("PST", null);
        zoneIdAndFlag.put("UTC", null);
        zoneIdAndFlag.put("GMT+00", null);
        for (int offset = 1; offset < 13; offset++) {
            String normalized_offset = String.format("%02d", offset);
            zoneIdAndFlag.put("GMT+" + normalized_offset, null);
            zoneIdAndFlag.put("GMT-" + normalized_offset, null);
        }

        return zoneIdAndFlag;
    }
}