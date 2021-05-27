package pl.lodz.p.it.ssbd2021.ssbd01.utils.converters;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AccessLevelMapper;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common.AccessLevelDto;

public class AccessLevelConverter {

    /**
     * Tworzy nową instancję obiektu AccessLevelEntity korzystając z danych z obiektu AccessLevelDto (konwertuje obiekt).
     *
     * @param accessLevelDto obiekt typu AccessLevelDto
     * @return obiekt typu AccessLevel
     * @throws AccessLevelException access level exception
     */
    public static AccessLevel createAccessLevelEntityFromDto(AccessLevelDto accessLevelDto) throws AccessLevelException {
        return AccessLevelMapper.mapLevelNameToAccessLevel(accessLevelDto.getLevel());
    }
}
