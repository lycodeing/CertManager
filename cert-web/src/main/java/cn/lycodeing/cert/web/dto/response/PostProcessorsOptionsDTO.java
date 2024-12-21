package cn.lycodeing.cert.web.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author lycodeing
 */
@Builder
@Data
public class PostProcessorsOptionsDTO {

    private Integer postId;


    private String postName;


    private String postType;
}
