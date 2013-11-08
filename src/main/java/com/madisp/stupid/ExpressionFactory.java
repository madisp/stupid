package com.madisp.stupid;

import com.madisp.stupid.expr.AndExpression;
import com.madisp.stupid.expr.ApplyExpression;
import com.madisp.stupid.expr.AssignExpression;
import com.madisp.stupid.expr.BlockExpression;
import com.madisp.stupid.expr.CallExpression;
import com.madisp.stupid.expr.ConstantExpression;
import com.madisp.stupid.expr.DivisionExpression;
import com.madisp.stupid.expr.MinusExpression;
import com.madisp.stupid.expr.MultiplicationExpression;
import com.madisp.stupid.expr.NegateExpression;
import com.madisp.stupid.expr.NotExpression;
import com.madisp.stupid.expr.OrExpression;
import com.madisp.stupid.expr.PlusExpression;
import com.madisp.stupid.expr.StatementListExpression;
import com.madisp.stupid.expr.TernaryExpression;
import com.madisp.stupid.expr.VarExpression;
import com.madisp.stupid.gen.StupidBaseVisitor;
import com.madisp.stupid.gen.StupidLexer;
import com.madisp.stupid.gen.StupidParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExpressionFactory extends StupidBaseVisitor<Value> {
	public Value parseExpression(String expression) {
		StupidLexer lexer = new StupidLexer(new ANTLRInputStream(expression));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		StupidParser parser = new StupidParser(tokens);
		return visitProg(parser.prog());
	}

	@Override
	public Value visitProg(@NotNull StupidParser.ProgContext ctx) {
		List<Value> statements = new ArrayList<Value>();
		StupidParser.StatementsContext xs = ctx.statements();
		while (xs != null) {
			statements.add(visitExpr(xs.expr()));
			xs = xs.statements();
		}
		return new StatementListExpression(statements);
	}

	@Override
	public Value visitExpr(@NotNull StupidParser.ExprContext ctx) {
		if (ctx.value() != null) {
			return visitValue(ctx.value());
		} if (ctx.AND() != null) {
			return new AndExpression(visitExpr(ctx.left), visitExpr(ctx.right));
		} else if (ctx.MINUS() != null && ctx.left != null) {
			return new MinusExpression(visitExpr(ctx.left), visitExpr(ctx.right));
		} else if (ctx.PLUS() != null) {
			return new PlusExpression(visitExpr(ctx.left), visitExpr(ctx.right));
		} else if (ctx.OR() != null) {
			return new OrExpression(visitExpr(ctx.left), visitExpr(ctx.right));
		} else if (ctx.STAR() != null) {
			return new MultiplicationExpression(visitExpr(ctx.left), visitExpr(ctx.right));
		} else if (ctx.SLASH() != null) {
			return new DivisionExpression(visitExpr(ctx.left), visitExpr(ctx.right));
		} else if (ctx.LPAREN() != null) {
			if (ctx.DOT() != null) {
				// apply, not value
				StupidParser.ArgslistContext args = ctx.argslist();
				List<Value> argsList = new ArrayList<Value>();
				while (args != null) {
					argsList.add(visitExpr(args.expr()));
					args = args.argslist();
				}
				return new ApplyExpression(visitExpr(ctx.left), argsList.toArray(new Value[argsList.size()]));
			}
			return visitExpr(ctx.center);
		} else if (ctx.BANG() != null) {
			return new NotExpression(visitExpr(ctx.center));
		} else if (ctx.MINUS() != null) {
			return new NegateExpression(visitExpr(ctx.center));
		} else if (ctx.var() != null) {
			Value base = null;
			if (ctx.DOT() != null) {
				base = visitExpr(ctx.left);
			}
			return new VarExpression(base, ctx.var().IDENTIFIER().getText());
		} else if (ctx.call() != null) {
			StupidParser.ArgslistContext args = ctx.call().argslist();
			List<Value> argsList = new ArrayList<Value>();
			while (args != null) {
				argsList.add(visitExpr(args.expr()));
				args = args.argslist();
			}
			Value base = null;
			if (ctx.DOT() != null) {
				base = visitExpr(ctx.left);
			}
			return new CallExpression(base, ctx.call().IDENTIFIER().getText(),
					argsList.toArray(new Value[argsList.size()]));
		} else if (ctx.ASK() != null) {
			return new TernaryExpression(visitExpr(ctx.left), visitExpr(ctx.center), visitExpr(ctx.right));
		} else if (ctx.assign() != null) {
			Value base = null;
			if (ctx.DOT() != null) {
				base = visitExpr(ctx.left);
			}
			return new AssignExpression(base, ctx.assign().IDENTIFIER().getText(), visitExpr(ctx.assign().expr()));
		}
		return super.visitExpr(ctx);
	}

	@Override
	public Value visitValue(@NotNull StupidParser.ValueContext ctx) {
		if (ctx.bool() != null) {
			return visitBool(ctx.bool());
		} else if (ctx.str() != null) {
			return visitStr(ctx.str());
		} else if (ctx.num() != null) {
			return visitNum(ctx.num());
		} else if (ctx.nil() != null) {
			return visitNil(ctx.nil());
		} else if (ctx.block() != null) {
			return visitBlock(ctx.block());
		}
		return super.visitValue(ctx);
	}

	@Override
	public Value visitBool(@NotNull StupidParser.BoolContext ctx) {
		if (ctx.TRUE() != null) {
			return new ConstantExpression(true);
		} else if (ctx.FALSE() != null) {
			return new ConstantExpression(false);
		}
		return super.visitBool(ctx);
	}

	@Override
	public Value visitStr(@NotNull StupidParser.StrContext ctx) {
		return new ConstantExpression(CharSupport.getStringFromGrammarStringLiteral(ctx.getText()));
	}

	@Override
	public Value visitNum(@NotNull StupidParser.NumContext ctx) {
		if (ctx.INT() != null) {
			return new ConstantExpression(Integer.parseInt(ctx.getText()));
		} else if (ctx.DOUBLE() != null) {
			return new ConstantExpression(Double.parseDouble(ctx.getText()));
		}
		return super.visitNum(ctx);
	}

	@Override
	public Value visitNil(@NotNull StupidParser.NilContext ctx) {
		return new ConstantExpression(null);
	}

	@Override
	public Value visitBlock(@NotNull StupidParser.BlockContext ctx) {
		StupidParser.VarlistContext vars = ctx.varlist();
		List<String> varsList = new ArrayList<String>();
		while (vars != null) {
			varsList.add(vars.IDENTIFIER().getText());
			vars = vars.varlist();
		}

		List<Value> statements = new ArrayList<Value>();
		StupidParser.StatementsContext xs = ctx.statements();
		while (xs != null) {
			statements.add(visitExpr(xs.expr()));
			xs = xs.statements();
		}

		return new BlockExpression(varsList.toArray(new String[varsList.size()]), new StatementListExpression(statements));
	}
}
