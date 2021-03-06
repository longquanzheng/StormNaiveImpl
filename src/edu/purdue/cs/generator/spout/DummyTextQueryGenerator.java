package edu.purdue.cs.generator.spout;

import java.util.Map;
import java.util.Random;

import edu.cs.purdue.edu.helpers.Constants;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class DummyTextQueryGenerator extends BaseRichSpout {

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
		i++;
		{
			int id =i ;

			String queryText = "Purdue";
			this.collector.emit(new Values(id,queryText));
			try {
				Thread.sleep(Constants.queryGeneratorDelay);
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
		declarer.declare(new Fields(Constants.queryIdField,
												  Constants.queryTextField));
	}
}
