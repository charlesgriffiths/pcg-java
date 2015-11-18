package pcg.rng;


abstract public class PermutedRNG extends SeekableRNG
{
protected ISeekableRNG source;
protected int inputByteAmount, outputByteAmount;


  protected PermutedRNG() {}


  protected PermutedRNG( SeekableRNG rng, int in, int out )
  {
    source = rng;
    inputByteAmount = in;
    outputByteAmount = out;
  }


  public static PermutedRNG createXORShift( SeekableRNG rng, int in, int out )
  {
    if (8 == in && 4 == out) return new XORShiftRNG_64_32( rng, in, out );

    return null;
  }


  @Override
  public void setState( byte[] b )
  {
    source.setState( b );
  }


  @Override
  public void seek( long position )
  {
    source.seek( position / outputByteAmount * inputByteAmount );
  }


  @Override
  public void advance( long amount )
  {
    source.advance( amount / outputByteAmount * inputByteAmount );
  }


  @Override
  protected void deepCopy( ISeekableRNG target )
  {
    if (target instanceof PermutedRNG)
    {
    PermutedRNG pr = (PermutedRNG) target;

      pr.source = source.deepCopy();
      pr.inputByteAmount = inputByteAmount;
      pr.outputByteAmount = outputByteAmount;
    }
  }

}


class XORShiftRNG_64_32 extends PermutedRNG
{
  private XORShiftRNG_64_32() {}


  XORShiftRNG_64_32( SeekableRNG rng, int in, int out )
  {
    super( rng, in, out );
  }


  @Override
  public int next32()
  {
  long input = source.next64();

//this particular 64b to 32b shift-rotation from https://github.com/imneme/pcg-cpp/blob/master/include/pcg_random.hpp
    return Integer.rotateRight( (int) (((input >>> 18) ^ input) >>> 27), (int) (input >>> 59) );
  }


  @Override
  public ISeekableRNG deepCopy()
  {
  ISeekableRNG target = new XORShiftRNG_64_32();
    
    deepCopy( target );

    return target;
  }
}

