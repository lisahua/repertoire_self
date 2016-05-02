/**
 * @author Lisa Apr 30, 2016 SingleTypeStmtStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Function.FunctionCreator;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtReturn;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.ast.core.typs.TypePrimitive;

public class ConditionStrategy extends CandidateStrategy {
	public final String insertFuncName = "__condition";
	private HashMap<String, Statement> typeInsertMap;

	public ConditionStrategy(RepairMultiController controller) {
		super(controller);
	}

	@Override
	public List<AtomicRunModel> generateModel(AtomicRunModel model, List<String> types,
			HashMap<String, Statement> typeInsertMap) {

		List<AtomicRunModel> models = new ArrayList<AtomicRunModel>();
		// FIXME hacky!
		if (model.getFunc().contains("Harness") || model.getFunc().contains("new"))
			return models;
		this.typeInsertMap = typeInsertMap;

		Function func = controller.getFuncMap(model.getFunc());
		HashMap<String, Type> varTypes = controller.getNameResolver().getTypeMap(model.getFunc());
		HashMap<String, Type> typeMap = new HashMap<String, Type>();
		List<Parameter> paraList = new ArrayList<Parameter>();
		if (varTypes == null)
			return models;
		// System.out.println("condition var type is " + varTypes);
		for (String name : varTypes.keySet()) {
			paraList.add(new Parameter(func.getOrigin(), varTypes.get(name), name));
			String typeName = varTypes.get(name).toString();
			if (typeName.contains("@"))
				typeMap.put(typeName.substring(0, typeName.indexOf("@")), varTypes.get(name));
			else
				typeMap.put(typeName, varTypes.get(name));
		}
		
		
		
		typeMap.put("int", TypePrimitive.int32type);
		typeMap.put("double", TypePrimitive.doubletype);
		typeMap.put("bit", TypePrimitive.bittype);

		FunctionCreator creator = new FunctionCreator(func.getOrigin());
		creator = creator.name(insertFuncName);
		creator = creator.returnType(TypePrimitive.bittype);
		creator = creator.params(paraList);
//		creator = creator.params(func.getParams());
		// FIXME maybe buggy
		System.out.println("condition type is" + types + "," + typeMap);
		// for (String type : types) {
		for (String type : typeMap.keySet()) {
			creator = generateConditionFunc(model, typeMap.get(type), typeInsertMap.get(type), creator);
			model.setInsertFunction(creator.create());
			if (model.getInsertFunction() == null)
				continue;
			if (model.getInsertFunction().getBody() == null)
				continue;
			List<Statement> ifStmts = generateIfInsert(model, paraList);
			for (Statement ifSt : ifStmts) {
				model.setInsertStmt(ifSt);

				// System.out.println("into condition strategy " + model + "
				// [func]"
				// + model.getInsertFunction() + ",");
				// + model.getInsertFunction().getBody());

				for (int i = model.getLocation() + 2; i > 0; i--) {
					AtomicRunModel md = model.clone();
					md.setLocation(i);
					// System.out.println("into condition strategy " + model + "
					// [func]"
					// + model.getInsertFunction() + ","
					// + model.getInsertFunction().getBody());
					models.add(md);

				}
			}
		}
		return models;
	}

	private FunctionCreator generateConditionFunc(AtomicRunModel model, Type type, Statement typeInsert,
			FunctionCreator creator) {
		List<Statement> stmts = new ArrayList<Statement>();
		// System.out.println("generate condition "+ typeInsert.getClass());
		// if (!(typeInsert instanceof StmtAssign))
		// return creator;
		if (type == null || typeInsert == null)
			return creator;
		Statement tmp = typeInsert;
		while (tmp instanceof StmtBlock)
			tmp = ((StmtBlock) tmp).getStmts().get(0);
		StmtAssign assign = (StmtAssign) tmp;
		// System.out.println("generate if condition " + type + "," + assign);
		List<Statement> insertIfBlock = new ArrayList<Statement>();
		insertIfBlock.addAll(typeInsertMap.values());
//		stmts.add(new StmtBlock(typeInsert.getOrigin(),insertIfBlock));
		stmts.add(new StmtVarDecl(typeInsert.getOrigin(), type, "lhs", assign.getRHS()));
		stmts.add(new StmtVarDecl(typeInsert.getOrigin(), type, "rhs", assign.getRHS()));
		ExprRegen exp = new ExprRegen(typeInsert.getOrigin(), "{| lhs== rhs|lhs != rhs| true |}");
		stmts.add(new StmtVarDecl(typeInsert.getOrigin(), TypePrimitive.bittype, "rst", exp));
		stmts.add(new StmtReturn(typeInsert.getOrigin(), new ExprVar(typeInsert.getOrigin(), "rst")));
		return creator.body(new StmtBlock(typeInsert.getOrigin(), stmts));

	}

	private List<Statement> generateIfInsert(AtomicRunModel model, List<Parameter> paraList) {
		Function func = controller.getFuncMap(model.getFunc());
		Type returnType = func.getReturnType();
		List<Statement> ifConds = new ArrayList<Statement>();
		List<Expression> params = new ArrayList<Expression>();
		for (Parameter pr : paraList)
			params.add(new ExprVar(func.getOrigin(), pr.getName()));
//		for (Parameter pr :func.getParams())
//		params.add(new ExprVar(func.getOrigin(), pr.getName()));
		Expression cond = new ExprFunCall(func.getOrigin(), insertFuncName, params);
	

		if (returnType.equals(TypePrimitive.bittype)) {
			// StmtReturn rtn = new StmtReturn(func.getOrigin(),
			// ExprConstInt.zero);
			ifConds.add(
					new StmtIfThen(func.getOrigin(), cond, new StmtReturn(func.getOrigin(), ExprConstInt.zero), null));
			ifConds.add(
					new StmtIfThen(func.getOrigin(), cond, new StmtReturn(func.getOrigin(), ExprConstInt.one), null));
			return ifConds;
		} else if (returnType.equals(TypePrimitive.voidtype)) {
			ifConds.add(new StmtIfThen(func.getOrigin(), cond, new StmtReturn(func.getOrigin(), null), null));
			return ifConds;
		} else {
			StmtAssign insert = (StmtAssign) ((StmtBlock) typeInsertMap.get(returnType.toString())).getStmts().get(0);
			StmtReturn rtn = new StmtReturn(func.getOrigin(), insert.getRHS());
			ifConds.add(new StmtIfThen(func.getOrigin(), cond, rtn, null));
			return ifConds;
		}

	}
}
