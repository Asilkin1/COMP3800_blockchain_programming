class Main {
    public static void main(String[] args) {
        // Test program
        Blockchain myBlockchain = new Blockchain();
        myBlockchain.addBlock("block 1");
        myBlockchain.addBlock("block 2");
        myBlockchain.print(); // print the whole arraylist rather than each block
        System.out.printf("This blockchain is %s", Blockchain.isValidChain(myBlockchain) ? "valid" : "invalid");
    }
}