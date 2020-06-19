package com.jeesite.modules.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class StaticDataComponent {
    @Value("${data.old.channel.bohai}")
    private String oldChannelBohai ;
    @Value("${data.old.channel.jiexin}")
    private String oldChannelJiexin ;




}
