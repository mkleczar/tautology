package org.example.tautology.visitor;

import org.example.tautology.Expression;

public interface ExpressionVisitor {
    void visiting(Expression ex);
    void visiting(Expression.Variable ex);
    void visiting(Expression.Const ex);
    void visiting(Expression.UnaryExpression ex);
    void visiting(Expression.BinaryExpression ex);
}
