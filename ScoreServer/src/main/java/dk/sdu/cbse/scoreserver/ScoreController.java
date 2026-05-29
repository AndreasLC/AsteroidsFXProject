package dk.sdu.cbse.scoreserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ScoreController {

    private final AtomicInteger score = new AtomicInteger(0);
    private final AtomicInteger lives = new AtomicInteger(3);

    @GetMapping("/score")
    public String getScore() {
        return String.valueOf(score.get());
    }

    @PostMapping("/score")
    public String addScore(@RequestBody String body) {
        int points = Integer.parseInt(body.trim());
        return String.valueOf(score.addAndGet(points));
    }

    @GetMapping("/lives")
    public String getLives() {
        return String.valueOf(lives.get());
    }

    @PutMapping("/lives")
    public String setLives(@RequestBody String body) {
        lives.set(Integer.parseInt(body.trim()));
        return String.valueOf(lives.get());
    }
}
