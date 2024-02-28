package org.example.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class FrameUser implements Serializable {
    private String id;
    private String loginid;
    private String password;
    private String ouguid;
}
