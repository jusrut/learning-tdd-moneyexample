class Money implements Expression {
    protected int amount;
    protected String currency;

    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    public String currency() {
        return currency;
    }

    public boolean equals(Object object)
    {
        // for some reason - this needs to be cast from a generic
        // object to a Dollar to meet Java's notion of equivalence
        // When the objects are tested for equivalence if the object types
        // do not match the types declared in the method signature
        // then the overloading doesn't work, and the wrong version
        // of equals() is called
        Money money = (Money) object;
        return amount == money.amount
                && currency.equals(money.currency());
    }

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    public Money reduce(Bank bank, String to) {
        int rate = bank.rate(currency, to);
        return new Money(amount / rate, to);
    }
}
