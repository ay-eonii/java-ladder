package ladder.domain;

import java.util.Collections;
import java.util.List;

public class Players {

    private static final int MIN_PEOPLE_COUNT = 3;

    private final List<Player> players;

    public Players(List<Player> players) {
        validate(players);
        this.players = players;
    }

    private void validate(List<Player> players) {
        validateDuplication(players);
        validateCount(players);
    }

    private void validateDuplication(List<Player> players) {
        long uniqueCount = players.stream()
                .distinct()
                .count();

        if (uniqueCount != players.size()) {
            throw new IllegalArgumentException("참여자는 중복일 수 없습니다.");
        }
    }

    private void validateCount(List<Player> players) {
        if (players.size() < MIN_PEOPLE_COUNT) {
            throw new IllegalArgumentException("최소 인원은 세명입니다.");
        }
    }

    public int count() {
        return players.size();
    }

    public int findMaxNameLength() {
        return players.stream()
                .mapToInt(Player::getLength)
                .max()
                .orElse(0);
    }

    public List<Player> getNames() {
        return Collections.unmodifiableList(players);
    }

    public int findPosition(Name name) {
        if (players.contains(name)) {
            return players.indexOf(name);
        }
        throw new IllegalArgumentException("존재하지 않는 참여자입니다.");
    }
}
