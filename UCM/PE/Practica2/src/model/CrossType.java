package model;

public enum CrossType {
	MONOPOINT("Monopunto"),
	MULTIPOINT("Multipunto"),
	UNIFORM("Uniforme"),
	PARTIALLY_MAPPED("Emparejamiento Parcial (PMX)"),
	ORDERED("Cruce por Orden (OX)"),
	ORDERED_VARIANT_OXPP("OX con Posiciones Prioritarias"),
	ORDERED_VARIANT_OXOP("OX con Orden Prioritario"),
	CICLES("Ciclos (CX)"),
	EDGE_RECOMBINATION("Recombinación de rutas (ERX)"),
	ORDINAL_CODIFICATION("Codificación Ordinal"),
	SELF_METHOD_1("Método propio 1");
	
	private final String name;
	
	private CrossType(String n) {
		this.name = n;
	};
	
	@Override
	public String toString() { return this.name; };
}
