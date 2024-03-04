package ladder.domain.ladder;

import ladder.domain.player.Name;
import ladder.domain.player.Player;
import ladder.domain.player.Players;
import ladder.domain.result.PlayResults;
import ladder.domain.result.Result;
import ladder.domain.result.Results;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LadderGameTest {

    private final RandomPointsGenerator pointsGenerator1 = new RandomPointsGenerator() {
        @Override
        public List<Point> generate(int size) {
            return List.of(Point.ON, Point.OFF, Point.ON, Point.OFF);
        }
    };

    private final RandomPointsGenerator pointsGenerator2 = new RandomPointsGenerator() {
        @Override
        public List<Point> generate(int size) {
            return List.of(Point.OFF, Point.ON, Point.OFF, Point.OFF);
        }
    };

    private Players players;
    private Results results;
    private Ladder ladder;
    private final Result result1 = new Result("꽝");
    private final Result result2 = new Result("3000");
    private final Result result3 = new Result("꽝");
    private final Result result4 = new Result("5000");

    @BeforeEach
    void setUp() {
        players = new Players(List.of("pobi", "honux", "crong", "jk"));

        results = new Results(List.of(
                result1, result2, result3, result4
        ), 4);

        Line line1 = new Line(pointsGenerator1.generate(3));
        Line line2 = new Line(pointsGenerator2.generate(3));
        ladder = new Ladder(List.of(line1, line2));
    }

    @Test
    @DisplayName("이름으로 실행 결과를 도출한다")
    void play_GetPlayResults_hasPlayerAndResult() {
        // given
        LadderGame ladderGame = new LadderGame(players, results, ladder);
        Name name = new Name("pobi");

        // when
        PlayResults playResults = ladderGame.play(name);

        // then
        assertThat(playResults)
                .extracting("value")
                .asInstanceOf(InstanceOfAssertFactories.map(Player.class, Result.class))
                .containsEntry(new Player("pobi"), result3);
    }

    @Test
    @DisplayName("모든 이름으로 실행 결과를 도출한다")
    void play_GetAllPlayResults_hasAllPlayerAndResult() {
        // given
        LadderGame ladderGame = new LadderGame(players, results, ladder);
        Name all = new Name("all");

        // when
        PlayResults playResults = ladderGame.play(all);

        // then
        assertThat(playResults)
                .extracting("value")
                .asInstanceOf(InstanceOfAssertFactories.map(Player.class, Result.class))
                .containsAllEntriesOf(Map.of(
                        new Player("pobi"), result3,
                        new Player("honux"), result1,
                        new Player("crong"), result4,
                        new Player("jk"), result2
                ));
    }
}
