import java.util.ArrayList;
import java.util.Date;

public class Blockchain {
    // Add blockchain properties here
    public ArrayList<Block> blockchain;
    public static int difficulty;

    /**
     * Constructor
     */
    public Blockchain() {
        // Create a blockchain
        this.blockchain = new ArrayList<>();
        // Add a genesis block as the first one
        this.blockchain.add(Block.genesis());
        // Set difficulty
        this.difficulty = 3; // Higher the difficulty, longer it takes to mine next block
    }

    /**
     * Adds a new block to the blockchain
     * @param data Block data
     */
    public void addBlock(String data) {
        blockchain.add(Block.mineBlock(blockchain.get(blockchain.size()-1),data,difficulty));
    }

    /**
     * Prints out all blocks of a blockchain
     */
    public void print() {
        System.out.println(blockchain);
    }

    /**
     * Checks whether a blockchain is valid
     * Hints:
     * - Genesis block must be valid
     * - Hash of each block on the chain (except genesis block) must be valid
     * - The current's prevHash must match the previous block's hash
     * @param blockchain The blockchain to check
     * @return True if valid, false otherwise
     */
    public static boolean isValidChain(Blockchain blockchain) {
        Block current;
        Block previous;

        // Ignore block at index zero, since it is a genesis block
        for (int i = 1; i < blockchain.blockchain.size();i++){
            current = blockchain.blockchain.get(i);     // Current block
            previous = blockchain.blockchain.get(i - 1); // Previous block

            // Create new target
            String target = new String(new char[blockchain.difficulty]).replace('\0','0');

            // Hash of each block must be valid
            if(!current.hash.equals(current.calculateHash())){  // Have to change it here to make it work
                return false;
            }

            // The current's prevHash must match the previous block's hash
            if(!previous.hash.equals(current.prevHash)){
                return false;
            }

            if(!current.hash.substring(0,blockchain.difficulty).equals(target)){
                return false;
            }
        }
        return true;
    }
}
