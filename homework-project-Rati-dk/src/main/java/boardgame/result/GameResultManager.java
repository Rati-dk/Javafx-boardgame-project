package boardgame.result;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface GameResultManager {

    List<GameResult> add(GameResult result) throws IOException;

    List<GameResult> getAll() throws IOException;

    default List<PlayerStatistics> getBestPlayers(int limit) throws IOException {
        var winnerMap = getAll()
                .stream()
                .collect(Collectors.groupingBy(GameResult::getWinnerName, Collectors.counting()));
        return winnerMap.entrySet()
                .stream()
                .map(entry -> new PlayerStatistics(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(PlayerStatistics::getNumberOfWins).reversed())
                .limit(limit)
                .toList();
    }

}
