package rngtools;


public interface IBitStream
{
  public boolean next();

  public IBitStream deepCopy();
}

