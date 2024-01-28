package marketing.lottery.rpc.dto;

import lombok.Data;

/**
 * 分页数据
 * * @Author: jack
 * * @Date    2024/1/28 21:13
 * * @Description 相信坚持的力量！
 **/
@Data
public class PageData<T> {

    private Integer pageNo;

    private Integer pageSize;

    private T data;

}
