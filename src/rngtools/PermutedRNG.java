package rngtools;


abstract public class PermutedRNG extends RNG
{
protected IRNG source;
protected int inputByteAmount, outputByteAmount;


  protected PermutedRNG() {}


  protected PermutedRNG( RNG rng, int in, int out )
  {
    source = rng;
    inputByteAmount = in;
    outputByteAmount = out;
  }


  public static PermutedRNG createXORShift( RNG rng, int in, int out )
  {
    if (8 == in && 4 == out) return new XORShiftRNG_64_32( rng, in, out );

    return null;
  }


  @Override
  public void setState( byte b[] )
  {
    source.setState( b );
  }


  @Override
  public boolean seek( long position )
  {
    return source.seek( position / outputByteAmount * inputByteAmount );
  }


  @Override
  public void advance( long amount )
  {
    source.advance( amount / outputByteAmount * inputByteAmount );
  }


  @Override
  protected IRNG deepCopy( IRNG target )
  {
    if (target instanceof PermutedRNG)
    {
    PermutedRNG pr = (PermutedRNG) target;

      pr.source = source.deepCopy();
      pr.inputByteAmount = inputByteAmount;
      pr.outputByteAmount = outputByteAmount;
    }

    return target;
  }

}


class XORShiftRNG_64_32 extends PermutedRNG
{
  private XORShiftRNG_64_32() {}


  XORShiftRNG_64_32( RNG rng, int in, int out )
  {
    super( rng, in, out );
  }


  @Override
  public int blockSize()
  {
    return 4;
  }


  @Override
  public long nextBlock()
  {
  long input = source.nextl( 64 );

//this particular 64b to 32b shift-rotation from https://github.com/imneme/pcg-cpp/blob/master/include/pcg_random.hpp
//    return Integer.rotateRight( (int) (((input >>> 18) ^ input) >>> 27), (int) (input >>> 59) );
  
// this shift-rotation does not give preference to higher-order bits
    return Integer.rotateRight( (int) ((input >>> 32) ^ input), (int) (input >>> 29) % 32 );
  }


  @Override
  public IRNG deepCopy()
  {
  IRNG target = new XORShiftRNG_64_32();
    
    deepCopy( target );

    return target;
  }
}

