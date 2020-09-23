import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Block {
    private long nonce;
    public String hash;
    public String prevHash;
    private long timestamp;
    private String data;

    /**
     * Block constructor
     * @param hash Current block hash
     * @param prevHash Previous block hash
     * @param timestamp Time block is created, which is number of milliseconds since 1/1/1970
     * @param data Block data
     * @param nonce Nonce
     */
    public Block(String hash, String prevHash, String data, long timestamp, long nonce) {
        this.data = data;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.hash = calculateHash(); // Hash calculated at the moment of creation
        this.prevHash = prevHash;
    }

    /**
     * Creates genesis block with the following values
     * - hash: "0"
     * - prevHash: "0"
     * - timestamp: 0
     * - data: "genesis block"
     * - nonce: 0
     * @return Genesis block
     */
    public static Block genesis() {
        return new Block("0","0","genesis block",new Date().getTime(),0);
    }

    /**
     * Mines a block
     * @param lastBlock The last block on the current blockchain
     * @param data The data of the new block
     * @param difficulty Mining difficulty that determines the number of leading Os of the hash
     * @return A mined block
     */
    public static Block mineBlock(Block lastBlock, String data, int difficulty) {

        // Create a string with the number of leading zeroes equals to difficulty
        String target = new String(new char[difficulty]).replace('\0','0');
        int nonce = 0;  // local nonce
        // Check if the hash is below the target
        while(!lastBlock.hash.substring(0,difficulty).equals(target)){
            // Increment the nonce
            nonce++;
            //lastBlock.nonce++;
            // recalculate hash again
            lastBlock.hash = calculateHash(lastBlock.prevHash,data,new Date().getTime(),nonce);
        }

        // Return newly created block
        return new Block(lastBlock.hash, lastBlock.hash,data,new Date().getTime(),nonce);
    }

    /**
     * Calculates hash of the current block
     * @return Hash string
     */
    public String calculateHash() {
        return calculateHash(prevHash,data,timestamp,nonce);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // DO NOT MODIFY THIS METHOD
        Map<String, String> map = new HashMap<>();
        map.put("hash", this.hash);
        map.put("prevHash", this.prevHash);
        map.put("timestamp", Long.toString(this.timestamp));
        map.put("data", this.data);
        map.put("nonce", Long.toString(this.nonce));

        return String.format("%s", map.toString());
    }

    /**
     * Calculates hash based on SHA256 algorithm
     * @param prevHash Hash of previous block
     * @param data Block data
     * @param timestamp Time the block is created
     * @param nonce The nonce
     * @return Hash string
     */
    private static String calculateHash(String prevHash, String data, long timestamp, long nonce) {
        // DO NOT MODIFY THIS METHOD
        String blockStr = String.join(" ", Long.toString(timestamp), data, prevHash, Long.toString(nonce));
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(blockStr.getBytes(StandardCharsets.UTF_8));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String hex = Integer.toHexString(0xff & digest[i]);
                if(hex.length() == 1) hash.append('0');
                hash.append(hex);
            }
            return hash.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
