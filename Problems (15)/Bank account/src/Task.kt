// write the BankAccount class here
class BankAccount {

    constructor(deposited: Long, withdrawn: Long) {
        this.deposited = deposited
        this.withdrawn = withdrawn
        balance = deposited - withdrawn
    }

    var deposited: Long
    var withdrawn: Long
    val balance: Long
}