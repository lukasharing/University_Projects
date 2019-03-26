package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.math.plot.Plot2DPanel;

import model.CrossType;
import model.FunctionType;
import model.GeneticAlgorithm;
import model.MutationType;
import model.SelectionType;
import p2.CitiesChromosome;
import p2.FunctionCities;

public class Panel2 extends JFrame {

	private static final long serialVersionUID = 2569879142816556337L;

	Plot2DPanel plot;

	private JTextField size_population;
	private JTextField num_generations;
	private JTextField crossover_perc;
	private JTextField mutation_perc;
	
	// Algoritmos de selecci�n
	private List<SelectionType> selection_type = Arrays.asList(
		SelectionType.ROULETTE, 
		SelectionType.DETE_TOURNAMENT,
		SelectionType.PRB_TOURNAMENT,
		SelectionType.RANKING, 
		SelectionType.TRUNCATION
	);
	
	// Algoritmos de cruce
	private List<CrossType> cross_type = Arrays.asList(
		CrossType.PARTIALLY_MAPPED,
		CrossType.ORDERED,
		CrossType.ORDERED_VARIANT,
		CrossType.CICLES,
		CrossType.RECOMBINATION,
		CrossType.ORDINAL_CODIFICATION,
		CrossType.SELF_METHOD_1,
		CrossType.SELF_METHOD_2
	);
	
	// Algoritmos de mutaci�n
	private List<MutationType> mutation_type = Arrays.asList(
		MutationType.INSERTION,
		MutationType.SWAP,
		MutationType.INVERSION,
		MutationType.HEURISTIC,
		MutationType.SELF_METHOD_1,
		MutationType.SELF_METHOD_2
	);
	
	private JComboBox<String> selection_sel;
	private JComboBox<String> mutation_sel;
	private JComboBox<String> cross_sel;
	private JButton start;
	private JButton restart;
	//private JCheckBox elitism;
	private JSpinner elitism_amount;
	private GeneticAlgorithm<?> ga;

	public Panel2() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setResizable(false);

		setTitle("Pr�ctica 2");
		this.setMinimumSize(new Dimension(1300, 700));

//		// Components
//		plot = new Plot2DPanel();
//		plot.addLegend("SOUTH");
//		plot.setBorder(BorderFactory.createLineBorder(new Color(141, 179, 214)));
//		add(plot, BorderLayout.CENTER);

		// Components
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setBorder(BorderFactory.createLineBorder(new Color(141, 179, 214)));
		plot.setMinimumSize(new Dimension(200, 200));
		add(plot, BorderLayout.CENTER);

        
		JPanel barraizq = new JPanel();
		barraizq.setLayout(new BoxLayout(barraizq, BoxLayout.Y_AXIS));

		/* Titulo */
		JLabel titulo = new JLabel("PAR�METROS:");
		JPanel ptitle = new JPanel(new GridLayout(1, 1));
		titulo.setFont(titulo.getFont().deriveFont(16.0f));
		ptitle.add(titulo);
		barraizq.add(ptitle);


		/* Size Population */
		this.size_population = new JTextField("100", 12);
		JPanel p1 = new JPanel(new GridLayout(2, 1));
		p1.add(new JLabel("Tama�o poblaci�n:"));
		p1.add(size_population);
		barraizq.add(p1);

		/* Number Generations */
		this.num_generations = new JTextField("100", 12);
		JPanel p2 = new JPanel(new GridLayout(2, 1));
		p2.add(new JLabel("N�mero generaciones:"));
		p2.add(num_generations);
		barraizq.add(p2);

		/* Crossing Percentage */
		this.crossover_perc = new JTextField("0.6", 12);
		JPanel p3 = new JPanel(new GridLayout(2, 1));
		p3.add(new JLabel("Probabilidad de cruce:"));
		p3.add(crossover_perc);
		barraizq.add(p3);

		/* Mutation Percentage */
		this.mutation_perc = new JTextField("0.05", 12);
		JPanel p4 = new JPanel(new GridLayout(2, 1));
		p4.add(new JLabel("Probabilidad de mutaci�n:"));
		p4.add(mutation_perc);
		barraizq.add(p4);

		/* Precition */
//		
//		this.prec = new JTextField("0.0001", 12);
//		JPanel p5 = new JPanel(new GridLayout(2, 1));
//		p5.add(new JLabel("Precisi�n:"));
//		p5.add(prec);
//		barraizq.add(p5);
//		
//		/* Function Selection */
//		this.function_sel = new JComboBox<>(function_sel_ops);
//		JPanel p6 = new JPanel(new GridLayout(2, 1));
//		p6.add(new JLabel("Funci�n:"));
//		p6.add(function_sel);
//		barraizq.add(p6);
//		
//		/* Number Parameters */
//		this.func4_params = new JSpinner();
//		func4_params.setValue(3);
//		func4_params.setEnabled(false);
//		JPanel p7 = new JPanel(new GridLayout(2, 1));
//		p7.add(new JLabel("N�mero Argumentos:"));
//		p7.add(func4_params);
//		barraizq.add(p7);
//		 
		/* Selection Selection */
		this.selection_sel = new JComboBox<>(selection_type.stream().map(n -> n.toString()).toArray(String[]::new));
		JPanel p11 = new JPanel(new GridLayout(2, 1));
		p11.add(new JLabel("Selecci�n:"));
		p11.add(selection_sel);
		barraizq.add(p11);
		
		/* Mutation Selection */
		this.mutation_sel = new JComboBox<>(mutation_type.stream().map(n -> n.toString()).toArray(String[]::new));
		JPanel p12 = new JPanel(new GridLayout(2, 1));
		p12.add(new JLabel("Mutaci�n:"));
		p12.add(mutation_sel);
		barraizq.add(p12);
		
		/* Cross Selection */
		this.cross_sel = new JComboBox<>(cross_type.stream().map(n -> n.toString()).toArray(String[]::new));
		JPanel p13 = new JPanel(new GridLayout(2, 1));
		p13.add(new JLabel("Cruce:"));
		p13.add(cross_sel);
		barraizq.add(p13);


		/* Has Elitism */
		/*this.elitism = new JCheckBox("Elitismo");
		JPanel p8 = new JPanel(new GridLayout(1, 1));
		p8.add(elitism);
		barraizq.add(p8);*/

		/* Number Elitism */
		this.elitism_amount = new JSpinner();
		elitism_amount.setValue(5);
		JPanel p9 = new JPanel(new GridLayout(2, 1));
		p9.add(new JLabel("N�mero Elitismo:"));
		p9.add(elitism_amount);
		barraizq.add(p9);

		/* Buttons */
		JPanel p10 = new JPanel(new GridLayout(2, 1));
		start = new JButton("Iniciar");
		restart = new JButton("Restablecer");
		p10.add(start);
		p10.add(restart);
		barraizq.add(p10);

		barraizq.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		/* Footer */
		barraizq.setBorder(BorderFactory.createEmptyBorder(8, 15, 0, 15));
		add(barraizq, BorderLayout.LINE_START);

		JLabel footer = new JLabel("Realizado por Lukas Haring y Ra�l Torrijos", SwingConstants.CENTER);
		footer.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(footer, BorderLayout.PAGE_END);
		setVisible(true);

		// OUTPUT
		JPanel barradcha = new JPanel();

		barradcha.setLayout(new BorderLayout());
		barradcha.setBorder(BorderFactory.createEmptyBorder(8, 24, 0, 15));

		JPanel barradchactr = new JPanel();
		barradcha.add(barradchactr);
		barradchactr.setLayout(new BoxLayout(barradchactr, BoxLayout.Y_AXIS));
		JLabel titulodcha = new JLabel("RESULTADOS:       ");
		titulodcha.setFont(titulodcha.getFont().deriveFont(16.0f));

		restartResults(barradchactr, titulodcha);

		JPanel barradchaftr = new JPanel();
		barradcha.add(barradchaftr, BorderLayout.PAGE_END);
		barradchaftr.setLayout(new BoxLayout(barradchaftr, BoxLayout.Y_AXIS));
		barradchaftr.add(new JLabel("Mejor evaluaci�n:"), BorderLayout.PAGE_END);
		JLabel best_ev = new JLabel(" ");
		barradchaftr.add(best_ev);

		add(barradcha, BorderLayout.LINE_END);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				restartPlot();
				restartResults(barradchactr, titulodcha);

				int elitism_am = elitism_am = ((Integer) elitism_amount.getValue());

				int num_gen = Integer.parseInt(num_generations.getText());

				model.SelectionType type_sel = selection_type.get(selection_sel.getSelectedIndex());
				model.CrossType type_cross = cross_type.get(cross_sel.getSelectedIndex());
				model.MutationType type_mut = mutation_type.get(mutation_sel.getSelectedIndex());
				
				ga = new GeneticAlgorithm<CitiesChromosome>(
					CitiesChromosome.class,
					Integer.parseInt(size_population.getText()),
					num_gen,
					Double.parseDouble(crossover_perc.getText()),
					Double.parseDouble(mutation_perc.getText()),
					0.0, elitism_am, type_sel, type_cross, type_mut,
					new FunctionCities(FunctionType.MINIMIZE)
				);
				
				List<double[]> best_distances = ga.run();

				/*
				double[] generations = new double[num_gen];
				for (int i = 0; i < num_gen; ++i) {
					generations[i] = i;
				}

				addPlotLines(generations, best_chromosomes);
				*/
				
				for(double[] db : best_distances) {
					System.out.println(Arrays.toString(db));
				}
				
				plot.repaint();
				plot.revalidate();

		}});

		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartPlot();
				best_ev.setText(" ");
				restartResults(barradchactr, titulodcha);

				plot.repaint();
				plot.revalidate();
			}
		});

		/*elitism.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				elitism_amount.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
			}
		});*/


		
	}

    public void paint (Graphics g)
    {
        super.paint(g);

        g.setColor (Color.blue);
        g.drawLine (0, 70, 100, 70);
    }
	
	void restartResults(JPanel p, JLabel l) {
		p.removeAll();
		p.add(l);
		p.add(new JLabel(" "));
		p.revalidate();
		p.repaint();

	}

	void restartPlot() {
		remove(plot);
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		add(plot, BorderLayout.CENTER);
		repaint();
		validate();
	}

	void addPlotLines(double[] generations, List<double[]> best_chromosomes) {
		plot.addLinePlot("Mejor absoluto", generations, best_chromosomes.get(0));
		plot.addLinePlot("Mejor de la generaci�n", generations, best_chromosomes.get(1));
		plot.addLinePlot("Media de la generaci�n", generations, best_chromosomes.get(2));
	}
}