package org.example.tautology;

public class ExpressionAbstractVisitor implements ExpressionVisitor {
    @Override
    public void visiting(Expression ex) {
    }

    @Override
    public void visiting(Expression.Variable ex) {
    }

    @Override
    public void visiting(Expression.Const ex) {
    }

    @Override
    public void visiting(Expression.UnaryExpression ex) {
    }

    @Override
    public void visiting(Expression.BinaryExpression ex) {
    }
}
