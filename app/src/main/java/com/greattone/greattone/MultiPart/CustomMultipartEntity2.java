package com.greattone.greattone.MultiPart;




//@SuppressWarnings("deprecation")
//public class CustomMultipartEntity2 extends MultipartEntity {  
//	  
//	UpdateListener downloadListener=new UpdateListener() {
//		
//		@Override
//		public void handleStatus(String arg0, int arg1) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void handleProcess(long arg0, long arg1, String arg2) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		
//		@Override
//		public void handleCancel() {
//			// TODO Auto-generated method stub
//			
//		}
//	};
//  
//    public CustomMultipartEntity2(final UpdateListener listener) {  
//        super();  
//        this.downloadListener = listener;  
//    }  
//  
//    public CustomMultipartEntity2(final HttpMultipartMode mode,  
//            final UpdateListener listener) {  
//        super(mode);  
//        this.downloadListener = listener;  
//    }  
//  
//    public CustomMultipartEntity2(HttpMultipartMode mode, final String boundary,  
//            final Charset charset, final UpdateListener listener) {  
//        super(mode, boundary, charset);  
//        this.downloadListener = listener;  
//    }  
//  
//    @Override  
//    public void writeTo(OutputStream outstream) throws IOException {  
//        super.writeTo(new CountingOutputStream(outstream, this.downloadListener,getContentLength()));  
//    }  
//  
////    public static interface ProgressListener {  
////        void transferred(long num);  
////    }  
////  
//    public static class CountingOutputStream extends FilterOutputStream {  
//          
//        private final UpdateListener listener;  
//        private long transferred;  
//        private long maxsize;  
//  
//        public CountingOutputStream(final OutputStream out,  
//                final UpdateListener listener,long maxsize) {  
//            super(out);  
//            this.listener = listener;  
//            this.maxsize = maxsize;  
//            this.transferred = 0;  
//        }  
//  
//        public void write(byte[] b, int off, int len) throws IOException {  
////        	if (!isPause) {
//        		out.write(b, off, len);  
//            this.transferred += len;  
//            this.listener.handleProcess(transferred, maxsize, "");
////        	}
//        }  
//  
//        public void write(int b) throws IOException {  
////        	if (!isPause) {
//            out.write(b);  
//            this.transferred++;  
//            this.listener.handleProcess(transferred, maxsize, ""); 
////        	}
//        }  
////    	private boolean isPause;
////    	public void pause() {
////    		isPause = true;
////    	}
////    	public void resume() {
////    		isPause = false;
////    		isCancel = false;
////    		listener.handleStatus("重启", 3);
////    	}
////    	
////    	private boolean isCancel;
////    	public void cancel()
////    	{
////    		isCancel = true;
////    	}
//    }  
//  
//}  