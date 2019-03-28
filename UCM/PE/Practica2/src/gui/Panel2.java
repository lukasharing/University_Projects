package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
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
import model.Pair;
import p2.CitiesChromosome;
import p2.FunctionCities;
import p2.Provinces;

public class Panel2 extends JFrame {

	private static final long serialVersionUID = 2569879142816556337L;

	Plot2DPanel plot;
	JTabbedPane tabbedPane;
	JPanel tp0;
	JPanel tp1;

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
			UnsupportedLookAndFeelException, IOException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setResizable(false);

		setTitle("Pr�ctica 2");
		this.setMinimumSize(new Dimension(1300, 700));

		// Components
		tabbedPane = new JTabbedPane();
		
		
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setBorder(BorderFactory.createLineBorder(new Color(141, 179, 214)));
		plot.setMinimumSize(new Dimension(200, 200));
		
		for(int i = 0; i < Provinces.CITIES_TEXT.length; ++i) {
			System.out.println(i + " -> " + Provinces.CITIES_TEXT[i]);
		}
		System.out.println(Provinces.CITIES.length + " -> " + Provinces.CITIES_TEXT.length);

		
		tp0 = new JPanel();
		tp0.add(plot);
		tp0.setLayout(new GridLayout(1, 1));
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		final BufferedImage map = ImageIO.read(new File("./src/images/mapa.png"));
        
		tp1 = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
		    public void paintComponent(Graphics g){
				g.drawImage(map, 0, 0, this);
				for(int i = 0; i < Provinces.CITIES.length; ++i) {
					Pair<Integer, Integer> coords = Provinces.CITIES[i].getCoords();
					g.drawOval(coords.first-3, coords.second-3, 6, 6);
					//g.drawChars(data, offset, length, x, y);
					try {
						g.drawLine(Provinces.CITIES[(int) ga.getBest_chr().getFenotypes()[i]].getCoords().first, Provinces.CITIES[(int) ga.getBest_chr().getFenotypes()[i]].getCoords().second, Provinces.CITIES[(int) ga.getBest_chr().getFenotypes()[i+1]].getCoords().first, Provinces.CITIES[(int) ga.getBest_chr().getFenotypes()[i+1]].getCoords().second);
					} catch (Exception e) {
						//System.err.println("No se puedo imprimir el recorrido porque no hay resultado todav�a.");
					}
				}
		    };
		};
        tp1.setLayout(new GridLayout(1, 1));

		tabbedPane.addTab("Gr�fica", tp0);
        tabbedPane.addTab("Mapa", tp1);
        
		add(tabbedPane, BorderLayout.CENTER);

        
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

		/* Number Elitism */
		this.elitism_amount = new JSpinner();
		elitism_amount.setValue(0);
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
				tp1.repaint();
				tp1.revalidate();
				
				restartResults(barradchactr, titulodcha);

				int elitism_am = ((Integer) elitism_amount.getValue());

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
					new FunctionCities(27, FunctionType.MINIMIZE)
				);
				

				List<double[]> best_distances = ga.run();

				
				
				double[] generations = new double[num_gen];
				for (int i = 0; i < num_gen; ++i) {
					generations[i] = i;
				}
				
				addPlotLines(generations, best_distances);
				
				best_ev.setText(Integer.toString((int) best_distances.get(0)[best_distances.get(0).length - 1]) +" kms");

				for (int i = 0; i < ga.getBest_chr().getFenotypes().length; i++) {
					barradchactr.add(
						new JLabel(CitiesChromosome.parseCity((int) ga.getBest_chr().getFenotypes()[i]) + ((i == ga.getBest_chr().getFenotypes().length-1) ? " " : "->" )));
				}
				
			
		}});

		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ga = null;
				restartPlot();
				tp1.repaint();
				tp1.revalidate();
				best_ev.setText(" ");
				restartResults(barradchactr, titulodcha);

				plot.repaint();
				plot.revalidate();
			}
		});

	}
	
	void restartResults(JPanel p, JLabel l) {
		p.removeAll();
		p.add(l);
		p.add(new JLabel(" "));
		p.revalidate();
		p.repaint();

	}

	void restartPlot() {
		tp0.remove(plot);
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		tp0.add(plot, BorderLayout.CENTER);
	}

	void addPlotLines(double[] generations, List<double[]> best_chromosomes) {
		plot.addLinePlot("Mejor absoluto", generations, best_chromosomes.get(0));
		plot.addLinePlot("Mejor de la generaci�n", generations, best_chromosomes.get(1));
		plot.addLinePlot("Media de la generaci�n", generations, best_chromosomes.get(2));
	}
}
