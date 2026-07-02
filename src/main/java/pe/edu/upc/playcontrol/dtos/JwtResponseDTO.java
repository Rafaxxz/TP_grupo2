package pe.edu.upc.playcontrol.dtos;

import java.io.Serializable;

public class JwtResponseDTO implements Serializable {

    private final String jwttoken;

    public JwtResponseDTO(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}
