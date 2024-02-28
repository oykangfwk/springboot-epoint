package org.example.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
@ConfigurationProperties(prefix = "car")
@ToString
@Data //生成get\set方法
@ApiModel("实体Car")
public class Car {

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("名称")
    private String name;
}
