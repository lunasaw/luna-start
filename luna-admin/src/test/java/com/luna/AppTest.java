package com.luna;

import com.luna.generator.mapper.GenTableMapper;
import nonapi.io.github.classgraph.utils.Join;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author luna
 * 2022/5/9
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback(false)
public class AppTest {

    @Autowired
    private GenTableMapper genTableMapper;

    @Test
    public void atest() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("/Users/luna/project/luna-start/luna-system/src/categoryAttributeMenu.sql"));
        String join = Join.join(" ", strings);
        System.out.println(join);
        genTableMapper.executeSql(join);
    }
}
