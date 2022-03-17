package com.malongbao.io.protobuf;

import com.google.protobuf.Duration;
import com.google.protobuf.MessageLite;
import lombok.Data;

/**
 * Description：这个类写的不对
 * date: 2022/3/6 19:51
 *
 * @author Hy
 * @since JDK 1.8
 */
@Data
public class StudentPOJO {

    @Data
    static class Student {
        String name;
        Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public static MessageLite getDefaultInstance() {
            return null;
        }
    }
}
