package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Klasa narzędzi adresu ip.
 */
public class IpAddressUtils {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    /**
     * Pobiera adres IP z zapytania.
     *
     * @param request request
     * @return Adres IP
     */
    public static String getClientIpAddressFromHttpServletRequest(HttpServletRequest request) {
        for (var header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}
