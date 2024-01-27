package boardgame.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PlayerStatistics {

    @NonNull private String winnerName;
    private long numberOfWins;

}
