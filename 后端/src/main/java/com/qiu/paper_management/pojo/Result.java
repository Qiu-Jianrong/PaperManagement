package com.qiu.paper_management.pojo;


//统一响应结果

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
// 需要添加以生成getter setter（主要是getter），因为将其转换为json格式的时候需要用
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    // 也可以使用Lombok的@AllArgsConstructor
    public Result(Integer _code, String _message, T _data){this.code = _code;this.message = _message; this.data = _data;}
    // 泛型类的静态方法不能使用类的泛型！
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(1, message, null);
    }

}
