package sn.ksi.test_et_deploiement.karate;

import org.springframework.boot.test.context.SpringBootTest;

import com.intuit.karate.junit5.Karate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserKarateTests {

    @Karate.Test
    public Karate testAll(){
        return Karate.run()
                .relativeTo(getClass());
    }

}