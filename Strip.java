import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Strip {
    protected int length;
    protected int[] numbers;
    protected int filled;
    protected int gaps;

    protected int uncertainty;
    protected int maxStates;

    public Strip(int length, int[] numbers) {
        this.length = length;
        this.numbers = numbers;
        
        filled = Arrays.stream(numbers).sum();
        gaps = numbers.length - 1;
        uncertainty = length - filled - gaps;
        maxStates = Util.binomial(filled + gaps, filled);
    }

    public List<TileState[]> getStripStates(TileState[] currentLineState) {
        List<TileState[]> lineStates = new ArrayList<TileState[]>();
        TileState[] possibleLineState = new TileState[length];
        Arrays.fill(possibleLineState, TileState.EMPTY);
        
        if (gaps < 0 || numbers[0] == 0) {
            lineStates.add(possibleLineState);
        }
        else {
            recursiveStripState(currentLineState, possibleLineState, lineStates, 0, uncertainty, 0);
        }
        
        return lineStates;
    }

    public void recursiveStripState(TileState[] currentLineState, TileState[] possibleLineState, List<TileState[]> lineStates, int depth, int uncertainty, int startIndex) {
        
        // i is the distance of the piece from the start index
        // j is used to reset tiles to the blank state
        // k is the extension of the piece
        for (int i = 0; i <= uncertainty; i++) {
            for (int j = startIndex; j < length; j++) {
                possibleLineState[j] = TileState.EMPTY;
            }
            for (int k = 0; k < numbers[depth]; k++) {
                possibleLineState[i + startIndex + k] = TileState.FILLED;
            }

            if (depth < gaps) {
                recursiveStripState(currentLineState, possibleLineState, lineStates, depth + 1, uncertainty - i, startIndex + i + numbers[depth] + 1);
            }
            else {
                if (isPossibleLineState(currentLineState, possibleLineState)) {
                    lineStates.add(possibleLineState.clone());
                }
            }
        }
    }

    public boolean isPossibleLineState(TileState[] currentLineState, TileState[] possibleLineState) {
        for (int i = 0; i < length; i++) {
            if (!currentLineState[i].checkValidState(possibleLineState[i])) {
                return false;
            }
        }

        return true;
    }
}