package rngtools;


public interface IByteStream
{
  public int next();

  public IByteStream deepCopy();
}

