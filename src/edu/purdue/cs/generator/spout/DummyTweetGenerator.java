package edu.purdue.cs.generator.spout;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import edu.cs.purdue.edu.helpers.Constants;

public class DummyTweetGenerator extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private Random randomGenerator;
	int i = 0;
	

	public void ack(Object msgId) {
		System.out.println("OK:" + msgId);
	}

	public void close() {}

	public void fail(Object msgId) {
		System.out.println("FAIL:" + msgId);
	}
	

	@Override
	public void nextTuple() {
		while (true) {
			int id = randomGenerator.nextInt(Constants.numMovingObjects);
			int xCoord = randomGenerator.nextInt(Constants.xMaxRange);
			int yCoord = randomGenerator.nextInt(Constants.yMaxRange);
			String textContent = "Purdue is Awesome!";
			
			this.collector.emit(new Values(id, xCoord, yCoord, textContent));
			try {
				Thread.sleep(Constants.dataGeneratorDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		randomGenerator = new Random(Constants.generatorSeed);
	}


	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.objectIdField,
								    Constants.objectXCoordField,
								    Constants.objectYCoordField,
								    Constants.objectTextField));
	}
}