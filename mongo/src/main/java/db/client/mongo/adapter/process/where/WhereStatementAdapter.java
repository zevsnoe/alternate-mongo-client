package db.client.mongo.adapter.process.where;

import com.mongodb.BasicDBObject;
import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class WhereStatementAdapter extends WhereExpressionAdapter {

	//TODO: refactor, rid of parenthesis check, interface
	protected BasicDBObject adoptWhereStatement(Expression expression) {
		WhereExpressionVisitor visitor = new WhereExpressionVisitor();
		if (null != expression) {
			if (expression instanceof Parenthesis) {
				expression = ((Parenthesis) expression).getExpression();
			}
			expression.accept(visitor);
		}

		return visitor.result;
	}

	private class WhereExpressionVisitor implements ExpressionVisitor {
		private BasicDBObject result = new BasicDBObject();

		@Override
		public void visit(Parenthesis parenthesis) {
			return;
		}

		@Override
		public void visit(AndExpression expression) {
			result = adoptAndExpression(expression);
		}

		@Override
		public void visit(OrExpression expression) {
			result = adoptOrExpression(expression);
		}

		@Override
		public void visit(EqualsTo expression) {
			result = adoptEqualsToExpression(expression);
		}

		@Override
		public void visit(NotEqualsTo expression) {
			result = adoptNotEqualsToExpression(expression);
		}

		@Override
		public void visit(GreaterThan expression) {
			result = adoptGreaterThenExpression(expression);
		}

		@Override
		public void visit(MinorThan expression) {
			result = adoptMinorThenExpression(expression);
		}

		@Override
		public void visit(GreaterThanEquals expression) {
			result = adoptGreaterThenEqualsExpression(expression);
		}

		@Override
		public void visit(MinorThanEquals expression) {
			result = adoptMinorThenEqualsExpression(expression);
		}

		public void visit(InExpression inExpression) { throw new UnsupportedOperationException(); }
		public void visit(IsNullExpression isNullExpression) { throw new UnsupportedOperationException(); }
		public void visit(LikeExpression likeExpression) { throw new UnsupportedOperationException(); }
		public void visit(Between between) { throw new UnsupportedOperationException(); }
		public void visit(Column tableColumn) { throw new UnsupportedOperationException(); }
		public void visit(SubSelect subSelect) { throw new UnsupportedOperationException(); }
		public void visit(CaseExpression caseExpression) { throw new UnsupportedOperationException(); }
		public void visit(WhenClause whenClause) { throw new UnsupportedOperationException(); }
		public void visit(ExistsExpression existsExpression) { throw new UnsupportedOperationException(); }
		public void visit(AllComparisonExpression allComparisonExpression) { throw new UnsupportedOperationException(); }
		public void visit(AnyComparisonExpression anyComparisonExpression) { throw new UnsupportedOperationException(); }
		public void visit(Concat concat) { throw new UnsupportedOperationException(); }
		public void visit(Matches matches) { throw new UnsupportedOperationException(); }
		public void visit(BitwiseAnd bitwiseAnd) { throw new UnsupportedOperationException(); }
		public void visit(BitwiseOr bitwiseOr) { throw new UnsupportedOperationException(); }
		public void visit(BitwiseXor bitwiseXor) { throw new UnsupportedOperationException(); }
		public void visit(NullValue nullValue) { throw new UnsupportedOperationException(); }
		public void visit(Function function) { throw new UnsupportedOperationException(); }
		public void visit(InverseExpression inverseExpression) { throw new UnsupportedOperationException(); }
		public void visit(JdbcParameter jdbcParameter) { throw new UnsupportedOperationException(); }
		public void visit(DoubleValue doubleValue) { throw new UnsupportedOperationException(); }
		public void visit(LongValue longValue) { throw new UnsupportedOperationException(); }
		public void visit(DateValue dateValue) { throw new UnsupportedOperationException(); }
		public void visit(TimeValue timeValue) { throw new UnsupportedOperationException(); }
		public void visit(TimestampValue timestampValue) { throw new UnsupportedOperationException(); }
		public void visit(StringValue stringValue) { throw new UnsupportedOperationException(); }
		public void visit(Addition addition) { throw new UnsupportedOperationException(); }
		public void visit(Division division) { throw new UnsupportedOperationException(); }
		public void visit(Multiplication multiplication) { throw new UnsupportedOperationException(); }
		public void visit(Subtraction subtraction) { throw new UnsupportedOperationException(); }
	}

}
