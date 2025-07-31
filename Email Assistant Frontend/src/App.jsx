import "./App.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import React, { useState } from "react";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import SendIcon from "@mui/icons-material/Send";
import CircularProgress from "@mui/material/CircularProgress";

function App() {
  const [emailContent, setEmailContent] = useState("");
  const [generatedReply, setGeneratedReply] = useState("");
  const [tone, setTone] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {};

  return (
    <Container maxWidth="sm">
      <Typography variant="h4" component="h2">
        Email Reply Generator :
      </Typography>
      <Box component="section" sx={{ p: 2 }}>
        <TextField
          id="outlined-basic"
          label="Enter Your original email here"
          variant="outlined"
          fullWidth
          multiline
          rows={6}
          value={emailContent || ""}
          onChange={(e) => setEmailContent(e.target.value)}
        />

        <FormControl fullWidth sx={{ mt: 1 }}>
          <InputLabel>Tone(Optional)</InputLabel>
          <Select
            value={tone || ""}
            label="Tone(Optional)"
            onChange={(e) => setTone(e.target.value)}
          >
            <MenuItem value="none">None</MenuItem>
            <MenuItem value="professional">Professional</MenuItem>
            <MenuItem value="casual">Casual</MenuItem>
            <MenuItem value="friendly">Friendly</MenuItem>
            <MenuItem value="rejection">Rejection</MenuItem>
            <MenuItem value="Approval">Friendly</MenuItem>
            <MenuItem value="Feedback">Rejection</MenuItem>
          </Select>
        </FormControl>
        <Button
          endIcon={<SendIcon />}
          variant="contained"
          sx={{ mt: 1 }}
          disabled={!emailContent || loading}
          onClick={handleSubmit}
        >
          {loading ? <CircularProgress size={24} /> : "Generate Reply"}
        </Button>
      </Box>
      <Box component="section" sx={{ p: 1 }}>
        <TextField
          variant="outlined"
          fullWidth
          multiline
          rows={5}
          value={generatedReply || ""}
          inputProps={{ readOnly: true }}
        />
        <Button
          variant="contained"
          sx={{ mt: 1 }}
          onClick={() => {
            navigator.clipboard.writeText(generatedReply);
          }}
        >
          Copy Reply
        </Button>
      </Box>
    </Container>
  );
}

export default App;
